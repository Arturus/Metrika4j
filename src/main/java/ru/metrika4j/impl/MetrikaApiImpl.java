/*
 * Copyright (C) 2011 Artur Suilin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.metrika4j.impl;

import ru.metrika4j.*;
import ru.metrika4j.entity.*;
import ru.metrika4j.error.NoDataException;
import ru.metrika4j.error.ServerException;
import ru.metrika4j.http.HttpMethod;
import ru.metrika4j.http.HttpTransport;
import ru.metrika4j.json.JsonMapper;
import ru.metrika4j.json.JsonObject;

import java.lang.reflect.Array;
import java.util.Arrays;


public class MetrikaApiImpl implements MetrikaApi, ApiContext {
    private final HttpTransport transport;

    private final JsonMapper mapper;
    private final GoalApi goalApi = new GoalApiImpl();
    private final FilterApi filterApi = new FilterApiImpl();
    private final OperationApi operationApi = new OperationApiImpl();
    private final GrantApi grantApi = new GrantApiImpl();
    private final DelegateApi delegateApi = new DelegateApiImpl();
    private final AccountApi accountApi = new AccountApiImpl();


    public MetrikaApiImpl(HttpTransport transport, JsonMapper mapper) {
        this.transport = transport;
        this.mapper = mapper;
    }


    private class QueryBuilder<T> {
        private final String path;
        private final String objectName;
        private final Class<T> clazz;
        private String query;
        private HttpMethod httpMethod = HttpMethod.GET;
        private String input;

        private QueryBuilder(String path, Class<T> clazz, String objectName) {
            this.path = path;
            this.objectName = objectName;
            this.clazz = clazz;
        }

        QueryBuilder<T> withQuery(String query) {
            this.query = query;
            return this;
        }

        QueryBuilder<T> withQueryParam(String name, String value) {
            if (query == null) {
                query = "";
            }
            query += name + "=" + value;
            return this;
        }

        QueryBuilder<T> withMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        QueryBuilder<T> withInput(String input) {
            this.input = input;
            return this;
        }

        T get() {
            String url = path + ".json";
            if (query != null) {
                url = url + "?" + query;
            }
            JsonObject responseNode = getResponseByPath(url, httpMethod, input);

            if (clazz.isArray()) {
                JsonObject[] objects = responseNode.getObjectArray(objectName);
                Object[] result = (Object[]) Array.newInstance(clazz.getComponentType(), objects.length);
                for (int i = 0; i < objects.length; i++) {
                    result[i] = mapper.jsonObjectToEntity(objects[i], clazz.getComponentType());
                }
                //noinspection unchecked
                return (T) result;
            } else {
                JsonObject objectNode = responseNode.getObjectField(objectName);
                return mapper.jsonObjectToEntity(objectNode, clazz);
            }

        }
    }

    <T> QueryBuilder<T> q(String path, Class<T> clazz, String objectName) {
        return new QueryBuilder<T>(path, clazz, objectName);
    }


    private String makeCounterFields(CounterDetails... details) {
        if (details.length > 0) {
            String params = details[0].name();
            for (int i = 1; i < details.length; i++) {
                params = params + "," + details[i].name();
            }
            return "field=" + params;
        } else {
            return null;
        }

    }

    public Counter[] getCounters(CounterDetails... details) {
        return q("/counters", Counter[].class, "counters").withQuery(makeCounterFields(details)).get();
    }


    public Counter getCounter(int id, CounterDetails... details) {
        return q("/counter/" + id, Counter.class, "counter").withQuery(makeCounterFields(details)).get();
    }

    public ReportBuilder makeReportBuilder(Reports report, int counterId) {
        return new ReportBuilderImpl(report.url, counterId, this);
    }


    public JsonObject getResponseByUrl(String url) {
        return getResponseByUrl(url, HttpMethod.GET, null);
    }

    public JsonObject getResponseByUrl(String url, HttpMethod method, String input) {
        String content = transport.doRequest(url, method, input);
        JsonObject result = mapper.stringToJsonObject(content);
        JsonObject[] jsonErrors = result.getObjectArray("errors");
        if (jsonErrors != null) {
            MetrikaError errors[] = new MetrikaError[jsonErrors.length];
            for (int i = 0; i < errors.length; i++) {
                errors[i] = mapper.jsonObjectToEntity(jsonErrors[i], MetrikaError.class);
            }
            for (MetrikaError error : errors) {
                if ("ERR_NO_DATA".equals(error.getCode())) {
                    throw new NoDataException(Arrays.asList(jsonErrors).toString(), errors);
                }
            }
            throw new ServerException(Arrays.asList(jsonErrors).toString(), errors);
        }
        return result;
    }

    private String convertObjectToJson(Object obj) {
        return mapper.entityToString(obj);
    }

    public Counter createCounter(Counter newCounter) {
        String fields = makeCounterFields(CounterDetails.values());
        String body = convertObjectToJson(newCounter);
        return q("/counters", Counter.class, "counter").withQuery(fields)
                .withMethod(HttpMethod.POST)
                .withInput(body)
                .get();
    }

    public Counter updateCounter(Counter counter) {
        String fields = makeCounterFields(CounterDetails.values());
        String body = convertObjectToJson(counter);
        return q("/counter/" + counter.getId(), Counter.class, "counter").withQuery(fields)
                .withMethod(HttpMethod.PUT)
                .withInput(body)
                .get();
    }

    public void deleteCounter(int id) {
        q("/counter/" + id, Counter.class, "counter").withMethod(HttpMethod.DELETE).get();
    }

    public JsonObject getResponseByPath(String path) {
        return getResponseByPath(path, HttpMethod.GET, null);

    }

    public JsonObject getResponseByPath(String path, HttpMethod method, String input) {
        return getResponseByUrl("http://api-metrika.yandex.ru" + path, method, input);
    }


    public GoalApi getGoalApi() {
        return goalApi;
    }

    public FilterApi getFilterApi() {
        return filterApi;
    }


    public OperationApi getOperationApi() {
        return operationApi;
    }

    public GrantApi getGrantApi() {
        return grantApi;
    }


    public DelegateApi getDelegateApi() {
        return delegateApi;
    }

    public AccountApi getAccountApi() {
        return accountApi;
    }

    private class EntityApiImpl<T extends Entity<K>, K> implements EntityApi<T, K> {
        private final Class<T> entityClass;
        private final Class<T[]> entityArrayClass;
        private final String entityName;
        private final String entityPlural;

        public EntityApiImpl(Class<T> entityClass, Class<T[]> entityArrayClass, String entityName) {
            this.entityClass = entityClass;
            this.entityArrayClass = entityArrayClass;
            this.entityName = entityName;
            entityPlural = entityName + "s";
        }


        private String path(int counterId, String entityId) {
            return String.format("/counter/%d/%s/%s", counterId, entityName, entityId);
        }

        private String pluralPath(int counterId) {
            return String.format("/counter/%d/%s", counterId, entityPlural);
        }

        public T[] getEntities(int counterId) {
            return q(pluralPath(counterId), entityArrayClass, entityPlural).get();
        }

        public T getEntity(int counterId, K entityId) {
            return q(path(counterId, entityId.toString()), entityClass, entityName).get();
        }

        public T createEntity(int counterId, T source) {
            String body = convertObjectToJson(source);
            return q(pluralPath(counterId), entityClass, entityName).withMethod(HttpMethod.POST).withInput(body).get();
        }

        public T updateEntity(int counterId, T entity) {
            String body = "{'" + entityName + "':" + convertObjectToJson(entity) + "}";
            return q(path(counterId, entity.getId().toString()), entityClass, entityName).withMethod(HttpMethod.PUT)
                    .withInput(body)
                    .get();
        }

        public void deleteEntity(int counterId, K entityId) {
            q(path(counterId, entityId.toString()), entityClass, entityName).withMethod(HttpMethod.DELETE).get();
        }
    }

    private class GoalApiImpl extends EntityApiImpl<Goal, Integer> implements GoalApi {
        public GoalApiImpl() {
            super(Goal.class, Goal[].class, "goal");
        }
    }

    private class FilterApiImpl extends EntityApiImpl<Filter, Integer> implements FilterApi {
        public FilterApiImpl() {
            super(Filter.class, Filter[].class, "filter");
        }
    }


    private class OperationApiImpl extends EntityApiImpl<Operation, Integer> implements OperationApi {
        public OperationApiImpl() {
            super(Operation.class, Operation[].class, "operation");
        }
    }

    private class GrantApiImpl implements GrantApi {
        private final EntityApi<Grant, String> entityApi = new MetrikaApiImpl.EntityApiImpl<Grant, String>(Grant.class,
                Grant[].class, "grant");

        public Grant[] getGrants(int counterId) {
            return entityApi.getEntities(counterId);
        }

        public Grant getGrant(int counterId, String userLogin) {
            return entityApi.getEntity(counterId, userLogin);
        }

        public Grant createGrant(int counterId, Grant.Permission permission, String userLogin, String comment) {
            return entityApi.createEntity(counterId, new Grant(userLogin, permission, comment));
        }

        public Grant changeGrant(int counterId, Grant.Permission permission, String userLogin, String comment,
                                 boolean removeComment) {
            return entityApi.updateEntity(counterId,
                    new GrantRemoveComment(userLogin, permission, comment, removeComment));
        }

        public void deleteGrant(int counterId, String userLogin) {
            entityApi.deleteEntity(counterId, userLogin);
        }

        private class GrantRemoveComment extends Grant {
            private boolean commentRemove;

            GrantRemoveComment(String userLogin, Permission perm, String comment, boolean commentRemove) {
                super(userLogin, perm, comment);
                this.commentRemove = commentRemove;
            }

            public boolean isCommentRemove() {
                return commentRemove;
            }

            public void setCommentRemove(boolean commentRemove) {
                this.commentRemove = commentRemove;
            }
        }
    }

    private class AccountApiImpl implements AccountApi {

        public Account[] getAccounts() {
            return q("/accounts", Account[].class, "accounts").get();
        }

        public void deleteAccount(String user_login) {
            q("/account/" + user_login, Delegate.class, "delegate").withMethod(HttpMethod.DELETE).get();
        }

        public Account[] updateAccounts(Account[] accountList) {
            String body = "{'accounts':" + convertObjectToJson(accountList) + "}";
            return q("/accounts", Account[].class, "accounts").withMethod(HttpMethod.PUT).withInput(body).get();
        }
    }


    private class DelegateApiImpl implements DelegateApi {

        public Delegate[] getDelegates() {
            return q("/delegates", Delegate[].class, "delegates").get();
        }

        public Delegate[] addDelegate(String userLogin, String comment) {
            String body = "{'delegate':" + convertObjectToJson(new Delegate(userLogin, comment)) + "}";
            return q("/delegates", Delegate[].class, "delegates").withMethod(HttpMethod.POST).withInput(body).get();
        }

        public Delegate[] updateDelegateList(Delegate[] delegates) {
            String body = "{'delegates':" + convertObjectToJson(delegates) + "}";
            return q("/delegates", Delegate[].class, "delegates").withMethod(HttpMethod.PUT).withInput(body).get();
        }

        public void deleteDelegate(String userLogin) {
            q("/delegate/" + userLogin, Delegate.class, "delegate").withMethod(HttpMethod.DELETE).get();
        }
    }


}
