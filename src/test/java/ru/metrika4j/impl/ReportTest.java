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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.metrika4j.MetrikaDate;
import ru.metrika4j.Report;
import ru.metrika4j.Reports;
import ru.metrika4j.entity.Counter;
import ru.metrika4j.entity.CounterDetails;
import ru.metrika4j.error.NoDataException;
import ru.metrika4j.io.HttpUrlConnectionTransport;
import ru.metrika4j.json.jackson.JacksonMapper;

/** @author Artur Suilin */
public class ReportTest {
    MetrikaApiImpl api;
    Counter testCounter;

    @Test
    public void testReports() {
        for (Reports r : Reports.values()) {
            try {
                Report report = api.makeReportBuilder(r, testCounter.getId()).withDateFrom(new MetrikaDate()).build();
                Assert.assertEquals(testCounter.getId().intValue(), report.getCounterId());
                //Assert.assertNotNull(report.getData());
            } catch (NoDataException ignored) {
                System.out.println("No data for " + r);
            }
        }
    }


    @Before
    public void setup() {
        // Используем демо-аккаунт API Метрики, потому что в нем есть живые данные
        api = new MetrikaApiImpl(new HttpUrlConnectionTransport("05dd3dd84ff948fdae2bc4fb91f13e22"),
                new JacksonMapper());
        testCounter = api.getCounter(2138128, CounterDetails.values());

    }
}
