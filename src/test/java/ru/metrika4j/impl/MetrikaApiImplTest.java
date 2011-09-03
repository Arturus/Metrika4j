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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.metrika4j.DelegateApi;
import ru.metrika4j.FilterApi;
import ru.metrika4j.GoalApi;
import ru.metrika4j.GrantApi;
import ru.metrika4j.entity.*;
import ru.metrika4j.http.HttpUrlConnectionTransport;
import ru.metrika4j.json.jackson.JacksonMapper;


/** @author Artur Suilin */
public class MetrikaApiImplTest {
    private final static String OAUTH_TOKEN_PROPERTY = "TEST_OAUTH_TOKEN";
    private final static String GRANT_USER_PROPERTY = "TEST_GRANT_USER";
    Counter testCounter;
    Counter counterTemplate;
    MetrikaApiImpl api;
    String testUser;// = "astibuag";


    @Test
    public void testCreateCounter() {
        Assert.assertEquals(counterTemplate.getSite(), testCounter.getSite());
        Assert.assertEquals(counterTemplate.getName(), testCounter.getName());
        Assert.assertArrayEquals(counterTemplate.getMirrors(), testCounter.getMirrors());
        Assert.assertEquals(counterTemplate.getCodeOptions().async, testCounter.getCodeOptions().async);
        Assert.assertEquals(counterTemplate.getCodeOptions().clickmap, testCounter.getCodeOptions().clickmap);
    }


    @Test
    public void testUpdateAndGetCounter() {
        testCounter.setSite("test.net");
        api.updateCounter(testCounter);
        Counter counter = api.getCounter(testCounter.getId(), CounterDetails.values());
        Assert.assertEquals(testCounter.getSite(), counter.getSite());
    }


    @Test
    public void testGoalApi() {
        GoalApi goalApi = api.getGoalApi();
        Assert.assertArrayEquals(testCounter.getGoals(), goalApi.getEntities(testCounter.getId()));
        Goal existingGoal = testCounter.getGoals()[0];
        Goal testGoal = goalApi.getEntity(testCounter.getId(), existingGoal.getId());
        Assert.assertEquals(existingGoal, testGoal);

        existingGoal.setName("Changed goal");
        goalApi.updateEntity(testCounter.getId(), existingGoal);
        Assert.assertEquals(existingGoal, goalApi.getEntity(testCounter.getId(), existingGoal.getId()));

        goalApi.deleteEntity(testCounter.getId(), existingGoal.getId());
        Assert.assertTrue(goalApi.getEntities(testCounter.getId()).length == 0);
    }


    @Test
    public void testFilterApi() {
        FilterApi filterApi = api.getFilterApi();
        Assert.assertArrayEquals(testCounter.getFilters(), filterApi.getEntities(testCounter.getId()));

        Filter existingFilter = testCounter.getFilters()[0];
        Filter testFilter = filterApi.getEntity(testCounter.getId(), existingFilter.getId());
        Assert.assertEquals(existingFilter, testFilter);


        existingFilter.setValue("new_value");
        filterApi.updateEntity(testCounter.getId(), existingFilter);
        Assert.assertEquals(existingFilter, filterApi.getEntity(testCounter.getId(), existingFilter.getId()));

        filterApi.deleteEntity(testCounter.getId(), existingFilter.getId());
        Assert.assertTrue(filterApi.getEntities(testCounter.getId()).length == 1);
    }


    @Test
    public void testGrantApi() {
        GrantApi grantApi = api.getGrantApi();
        Assert.assertArrayEquals(testCounter.getGrants(), grantApi.getGrants(testCounter.getId()));
        Grant existingGrant = testCounter.getGrants()[0];
        Grant testGrant = grantApi.getGrant(testCounter.getId(), testUser);
        Assert.assertEquals(existingGrant, testGrant);

        grantApi.changeGrant(testCounter.getId(), Grant.Permission.edit, testUser, "changed", false);
        existingGrant = grantApi.getGrant(testCounter.getId(), testUser);
        Assert.assertEquals(existingGrant.getPerm(), Grant.Permission.edit);

        grantApi.deleteGrant(testCounter.getId(), testUser);
        Assert.assertTrue(grantApi.getGrants(testCounter.getId()).length == 0);
    }

    @Test
    public void testDelegateApi() {
        DelegateApi delegateApi = api.getDelegateApi();
        Delegate[] delegates = delegateApi.getDelegates();
        Assert.assertEquals(testUser, delegates[0].getUserLogin());
        delegateApi.deleteDelegate(testUser);
        Assert.assertTrue(delegateApi.getDelegates().length == 0);
        delegateApi.updateDelegateList(delegates);
        Assert.assertEquals(testUser, delegates[0].getUserLogin());

    }

    @Test
    public void testGetCounters() {
        Counter[] counters = api.getCounters(CounterDetails.values());
        boolean found = false;
        for (Counter counter : counters) {
            if (counter.getId().equals(testCounter.getId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);

    }


    @After
    public void teardown() {
        if (api != null) {
            if (testCounter != null) {
                api.deleteCounter(testCounter.getId());
            }
            api.getDelegateApi().deleteDelegate(testUser);
        }
    }


    @Before
    public void setup() {
        String oAuthToken = System.getenv(OAUTH_TOKEN_PROPERTY);
        if (oAuthToken == null) {
            throw new IllegalStateException("You should provide " + OAUTH_TOKEN_PROPERTY + " environment variable");
        }
        testUser = System.getenv(GRANT_USER_PROPERTY);
        if (testUser == null) {
            throw new IllegalStateException("You should provide " + GRANT_USER_PROPERTY + " environment variable");
        }
        api = new MetrikaApiImpl(new HttpUrlConnectionTransport(oAuthToken), new JacksonMapper());
        counterTemplate = new Counter();
        counterTemplate.setSite("test.ru");
        counterTemplate.setName("Тестовый счетчик");
        counterTemplate.setMirrors(new String[]{"test1.ru", "test2.ru"});
        Counter.CodeOptions options = new Counter.CodeOptions();
        counterTemplate.setCodeOptions(options);
        options.async = true;
        options.denial = false;
        options.clickmap = false;
        Goal testGoal = Goal.createUrlGoal("Тестовая цель", Goal.ConditionType.exact, "http://aa");
        counterTemplate.setGoals(new Goal[]{testGoal});
        Filter urlFilter = Filter.createFilter(Filter.Attr.url, Filter.Type.equal, "http://site.ru/url",
                Filter.Action.include);
        Filter mirrorFilter = Filter.createOnlyMirrorsFilter();
        counterTemplate.setFilters(new Filter[]{urlFilter, mirrorFilter});

        counterTemplate.setGrants(new Grant[]{new Grant(testUser, Grant.Permission.view, "test")});
        testCounter = api.createCounter(counterTemplate);
        api.getDelegateApi().addDelegate(testUser, "test");

    }
}
