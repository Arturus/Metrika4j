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
import ru.metrika4j.ReportItem;
import ru.metrika4j.json.JsonObject;

/** @author asuilin */
public class ReportImpl implements Report {
    private final ApiContext apiContext;
    private final String nextLink;
    private final MetrikaDate date1;
    private final MetrikaDate date2;
    private final ReportItem[] data;
    private final ReportItem totals;
    private final Integer goalId;
    private final int counterId;
    private final JsonObject o;

    public boolean hasNextPage() {
        return nextLink != null;
    }

    ReportImpl(JsonObject o, ApiContext apiContext) {
        this.apiContext = apiContext;
        this.o = o;
        counterId = o.getIntField("id");
        date1 = new MetrikaDate(o.getStringField("date1"));
        date2 = new MetrikaDate(o.getStringField("date2"));
        totals = o.hasField("totals") ? new ReportItemImpl(o.getObjectField("totals")) : null;
        goalId = o.hasField("goal_id") ? o.getIntField("goal_id") : null;
        data = getArray("data");
        nextLink = o.hasField("links") ? o.getObjectField("links").getStringField("next") : null;
    }

    public int getCounterId() {
        return counterId;
    }

    public Integer getGoalId() {
        return goalId;
    }

    public MetrikaDate getDateFrom() {
        return date1;
    }

    public MetrikaDate getDateTo() {
        return date2;
    }

    public ReportItem[] getData() {
        return data;
    }

    public ReportItem[] getArray(String name) {
        return new ReportItemImpl(o).getArray(name);
    }


    public ReportItem getTotals() {
        return totals;
    }

    public Report getNextPage() {
        return hasNextPage() ? new ReportImpl(apiContext.getResponseByUrl(nextLink), apiContext) : null;
    }

}
