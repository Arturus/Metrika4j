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

package ru.metrika4j;

import ru.metrika4j.entity.Counter;
import ru.metrika4j.entity.CounterDetails;
import ru.metrika4j.io.HttpTransport;
import ru.metrika4j.io.HttpUrlConnectionTransport;
import ru.metrika4j.json.jackson.JacksonMapper;

/** @author Artur Suilin */
public class Example {
    public static void main(String[] args) {
        // Создаём транспорт, передав в него OAuth токен
        HttpTransport transport = new HttpUrlConnectionTransport("05dd3dd84ff948fdae2bc4fb91f13e22");

        // Создаём экземпляр API
        MetrikaApi api = ApiFactory.createMetrikaAPI(transport, new JacksonMapper());

        // Получаем список счетчиков в текущем аккаунте
        Counter[] myCounters = api.getCounters(CounterDetails.values());
        for (Counter myCounter : myCounters) {
            System.out.println(myCounter.getSite());
        }


        // Создаём новый счетчик
        Counter newCounter = new Counter();
        newCounter.setSite("mysite.ru");
        newCounter.setName("Мой сайт");
        //api.createCounter(newCounter);

        // Удаляем счетчик
        //api.deleteCounter(newCounter.id);

        // Создаем построитель отчета "популярное содержимое" для счетчика с id=42
        ReportBuilder builder = api.makeReportBuilder(Reports.contentPopular, 2138128);

        // Задаём параметры отчета (отчет за неделю) и строим отчет
        Report report = builder.withDateFrom(MetrikaDate.yesterday()).withDateTo(MetrikaDate.today()).build();

        // Вытаскиваем результаты из отчета
        ReportItem[] items = report.getData();
        for (ReportItem item : items) {
            System.out.printf("pageViews: %4d, url: %s", item.getInt("page_views"), item.getString("url")).println();
        }

        // Строим такой же отчет, но древовидный
        report = builder.withTableMode(ReportBuilder.TableMode.tree).build();
        // Отображаем первые два уровня
        items = report.getData();
        for (ReportItem item : items) {
            // Первый уровень
            System.out
                    .printf("Level 1, pageViews: %4d, url: %s", item.getInt("page_views"), item.getString("url"))
                    .println();
            ReportItem[] children = item.getArray("chld");
            if (children != null) {
                for (ReportItem child : children) {
                    // Второй уровень
                    System.out
                            .printf("     Level 2, pageviews: %4d, url: %s", child.getInt("page_views"),
                                    child.getString("url"))
                            .println();
                }
            }
        }


    }

}
