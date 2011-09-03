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

import ru.metrika4j.MetrikaDate;
import ru.metrika4j.Report;
import ru.metrika4j.ReportBuilder;

import java.util.HashMap;
import java.util.Map;

/** @author asuilin */
public class ReportBuilderImpl implements ReportBuilder {
    private final ApiContext apiContext;
    private final PathBuilder pathBuilder;
    //private MetrikaDate dateFrom;
    //private MetrikaDate dateTo;


    public ReportBuilderImpl(String path, int counterId, ApiContext apiContext) {
        this.apiContext = apiContext;

        pathBuilder = new PathBuilder(path).add("id", counterId);
    }

    public ReportBuilder withDateFrom(MetrikaDate date) {
        //dateFrom = date;
        pathBuilder.add("date1", date);
        return this;
    }

    public ReportBuilder withDateTo(MetrikaDate date) {
        //dateTo = date;
        pathBuilder.add("date2", date);
        return this;
    }

    public ReportBuilder withGoal(Integer goalId) {
        if (goalId != 0) {
            pathBuilder.add("goal_id", goalId);
        }
        return this;
    }


    public ReportBuilder groupBy(ReportBuilder.Group group) {
        pathBuilder.add("group", group.toString());
        return this;
    }

    public ReportBuilder sortBy(String field) {
        pathBuilder.add("sort", field);
        return this;
    }

    public ReportBuilder reverseSortBy(String field) {
        pathBuilder.add("sort", field).add("reverse", 0);
        return this;
    }

    public ReportBuilder withTableMode(ReportBuilder.TableMode tm) {
        pathBuilder.add("table_mode", tm.toString());
        return this;
    }

    public ReportBuilder withMirrorId(Integer mirrorId) {
        if (mirrorId == null || mirrorId >= 0) {
            pathBuilder.add("mirror_id", mirrorId);
        } else {
            pathBuilder.add("mirror_id", "other");
        }
        return this;
    }

    public ReportBuilder withParameter(String name, String value) {
        pathBuilder.add(name, value);
        return this;
    }

    public ReportBuilder withItemsPerPage(Integer numberOfItems) {
        pathBuilder.add("per_page", numberOfItems);
        return this;
    }


    public Report build() {
        return new ReportImpl(apiContext.getResponseByPath(pathBuilder.buildPath()), apiContext);
    }


    /** Утилита для построения запроса к серверу */
    private class PathBuilder {
        private final String path;
        private final Map<String, String> params;


        PathBuilder(String path) {
            this.path = path;
            params = new HashMap<String, String>();
        }

        PathBuilder add(String name, String value) {
            if (value == null) {
                params.remove(name);
            } else {
                params.put(name, value);
            }
            return this;
        }

        PathBuilder add(String name, MetrikaDate value) {
            return add(name, value.toApiString());
        }


        PathBuilder add(String name, Integer value) {
            return add(name, value.toString());
        }

        String buildPath() {
            StringBuilder builder = new StringBuilder(path).append(".json");
            boolean firstPass = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.append(firstPass ? '?' : '&');
                firstPass = false;
                builder.append(entry.getKey()).append('=').append(entry.getValue());
            }
            return builder.toString();
        }

    }

}
