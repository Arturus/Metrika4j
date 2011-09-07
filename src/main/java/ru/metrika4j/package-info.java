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

/**
 *
 * Центральный класс Metrika4j - {@link ru.metrika4j.MetrikaApi}. В нем есть методы для работы с главными сущностями
 * Яндекс.Метрики:
 * счетчиками и отчетами. Работа с дополнительными сущностями (Цели, Фильтры, etc) вынесена в отдельные мини-API,
 * получаемые из главного
 * класса {@code MetrikaApi} c помощью методов {@code getXxxApi()}, например API целей {@link ru.metrika4j.MetrikaApi#getGoalApi()}.<br>
 * Структура методов примерно соответствует структуре REST-вызовов. Один REST-вызов = один метод.
 * Аргументы, передаваемые в REST-вызов, соответствуют аргументам, передаваемым в методы API (кроме API работы
 * с отчетами).<br>
 * Все сущности (счетчики, цели, фильтры, etc) - POJO объекты, без какой либо бизнес-логики.
 *
 * <h3>Подготовка к использованию Metrika4j </h3>
 * Прежде всего надо получить OAuth токен. В Metrika4j нет функций, связанных c OAuth авторизацией: предполагается,
 * что пользователь самостоятельно получает OAuth токен доступными ему способами, и передаёт в API уже готовый токен.
 * Процедура получения токена описана <a href="http://api.yandex.ru/metrika/doc/ref/concepts/authorization
 * .xml">здесь</a>.<br>
 * Далее, надо выбрать, с помощью чего обрабатывать JSON. В Metrika4j есть встроенная поддержка
 * <a href="http://jackson.codehaus.org">Jackson JSON-processor</a> (полноценный маппинг) и библиотеки org.json,
 * как lightweight
 * варианта, удобного для использования под Android (реализовано только чтение счетчиков и работа с отчетами).
 *  * Можно подключить любой другой JSON процессор, создав c его помощью имплементации интерфейсов {@link ru.metrika4j.json.JsonMapper}
 * и {@link ru.metrika4j.json.JsonObject}. Готовые имплементации, включенные в Metrika4j - это
 * {@link ru.metrika4j.json.jackson.JacksonMapper} и {@link ru.metrika4j.json.org.OrgJsonMapper}<br>
 *
 * <h3>Начало работы с Metrika4j</h3>
 * Сначала необходимо создать экземпляр {@link  ru.metrika4j.MetrikaApi} c помощью {@link ru.metrika4j.ApiFactory}
 * <pre>
 *    // Создаем экземпляр API, использующий Jackson JSON-processor и транспорт по умолчанию
 *    MetrikaApi api = ApiFactory.createMetrikaAPI("05dd3dd84ff948fdae2bc4fb91f13e22", new JacksonMapper());
 * </pre>
 * Если HTTP транспорт по умолчанию (использующий стандартный {@code HttpUrlConnection} из JDK) не устраивает,
 * можно создать свой транспорт, имплементировав  {@link ru.metrika4j.http.HttpTransport}. Транспорт должен получать
 * OAuth токен при создании и передавать его в каждом запросе к серверам Метрики.
 * <pre>
 *    // Создаём транспорт, передав в него OAuth токен
 *    HttpTransport transport = new MyTransport("05dd3dd84ff948fdae2bc4fb91f13e22");
 *
 *    // Создаем экземпляр API, использующий Jackson JSON-processor и заданный транспорт
 *    MetrikaApi api = ApiFactory.createMetrikaAPI(transport, new JacksonMapper());
 * </pre>
 * Полученный экземпляр API полностью thread-safe, поэтому на все приложение достаточно только одного экземпляра.
 *
 * <h3>Работа со счетчиками</h3>
 * <pre>
 *     // Получаем список счетчиков в текущем аккаунте
 *     Counter[] myCounters = api.getCounters();
 * </pre>
 * По умолчанию в полученных счетчиках не будет заполнена часть полей (цели, фильтры, зеркала и т.п.). Чтобы заполнить
 * их, надо при чтении счетчиков указать, какая детализация нужна
 * <pre>
 *     // Получаем счетчики с заполненными целями и фильтрами
 *     Counter[] myCounters = api.getCounters(CounterDetails.goals, CounterDetails.filters);
 *     // Получаем счетчики с полной детализацией
 *     Counter[] detailedCounters = api.getCounters(CounterDetails.values());
 * </pre>
 * При повышении детализации будет увеличиваться время ответа и объем передаваемой информации, поэтому лучше
 * не злоупотреблять высокой детализацией.
 * <pre>
 *     // Создание счетчика
 *     Counter newCounter = new Counter();
 *     newCounter.setSite("mysite.ru");
 *     newCounter.setName("Мой сайт");
 *     Counter createdCounter = api.createCounter(newCounter);
 *     // В createdCounter содержится новый счетчик, загруженный из Метрики, со всем значениями полей, проставленными Метрикой
 *     System.out.println(createdCounter.getId());
 * </pre>
 * <pre>
 *     // Удаление счетчика
 *     api.deleteCounter(createdCounter.id);
 * </pre>
 * <h3>Работа с отчетами</h3>
 * Набор доступных отчетов содержится в enum-e {@link ru.metrika4j.Reports}. Для построения отчета надо вызвать метод
 * {@link ru.metrika4j.MetrikaApi#makeReportBuilder(Reports, int)}, который вернет объект {@link ru.metrika4j.ReportBuilder}
 * - построитель отчета.
 * В построителе отчета можно задать параметры отчета (временной интервал, сортировку и т.п.). Когда все параметры будут
 * установлены, вызывайте метод {@link ru.metrika4j.ReportBuilder#build()}, который отправит запрос на сервер API
 * Метрики и вернет результат.
 * <pre>
 *    // Создаем построитель отчета "популярное содержимое" для счетчика с id=2138128
 *    ReportBuilder builder = api.makeReportBuilder(Reports.contentPopular, 2138128);
 *
 *    // Задаём параметры отчета (отчет за неделю) и строим отчет
 *    Report report = builder.withDateFrom(MetrikaDate.yesterday()).withDateTo(MetrikaDate.today()).build();
 * </pre>
 * Отчет возвращается в виде объекта {@link ru.metrika4j.Report}, представляющего собой таблицу с результатами, плюс некоторая
 * дополнительная информация (итоги, интервал дат, к которым относится отчет и т.п.). Каждая строка таблицы результатов -
 * объект {@link ru.metrika4j.ReportItem}, данные из которого можно получить одним из методов {@code getXXX(String fieldName)}
 * (аналогично получению значений из JDBC ResultSet). Имена полей и возвращаемые дополнительные данные надо уточнять
 * для каждого отчета в документации на API Яндекс.Метрики
 * <pre>
 *     // Вытаскиваем результаты из отчета
 *     ReportItem[] items = report.getData();
 *     for (ReportItem item : items) {
 *         System.out.printf("pageViews: %4d, url: %s", item.getInt("page_views"), item.getString("url")).println();
 *     }
 * </pre>
 * Некоторые отчеты бывают не только табличные, но и древовидные. Для получения древовидного отчета надо задать
 * в {@code ReportBuilder} соответствующий режим.
 * <pre>
 *   // Строим такой же отчет, но древовидный
 *   report = builder.withTableMode(ReportBuilder.TableMode.tree).build();
 *   // Отображаем первые два уровня
 *   items = report.getData();
 *   for (ReportItem item : items) {
 *       // Первый уровень
 *       System.out
 *               .printf("Level 1, pageViews: %4d, url: %s", item.getInt("page_views"), item.getString("url"))
 *               .println();
 *       ReportItem[] children = item.getArray("chld");
 *       if (children != null) {
 *           for (ReportItem child : children) {
 *               // Второй уровень
 *               System.out
 *                       .printf("     Level 2, pageviews: %4d, url: %s", child.getInt("page_views"),
 *                               child.getString("url"))
 *                       .println();
 *           }
 *       }
 *   }
 * </pre>
 * <h3>Обработка ошибок</h3>
 * Все ошибки, возникающие в ходе работы Metrika4j, унаследованы от базового класса {@link ru.metrika4j.error.Metrika4jException}.
 * Ошибки могут возникнуть только в момент обращения к серверам API Метрики. Картина такова:
 * <ul>
 *     <li>Если произошла ошибка в момент формирования обращения к серверу (не удалось сериализовать сущность в JSON), возникает {@link ru.metrika4j.error.JsonSerializationException}</li>
 *     <li>Если произошел сбой на транспортном уровне (ошибка сокета, плохой HTTP ответ), возникает {@link ru.metrika4j.error.TransportException}</li>
 *     <li>Если произошла ошибка авторизации (невалидный OAuth токен), возникает {@link ru.metrika4j.error.AuthException}</li>
 *     <li>Если сервер вернул JSON ответ, который не удалось распарсить, возникает {@link ru.metrika4j.error.ParseException}</li>
 *     <li>Если сервер вернул код ошибки (например при неправильных параметрах вызова), возникает
 *     {@link ru.metrika4j.error.ServerException}, в который передаются возвращенные сервером ошибки </li>
 *     <li>Если сервер Я.Метрики не нашел данных для отчета (например был задан интервал дат, в котором не содержится данных),
 *     возникает {@link ru.metrika4j.error.NoDataException}</li>
 * </ul>
 *
 *
 * <h3>Thread safety</h3>
 * Все основные классы Metrika4j не содержат состояния (stateless). API Я.Метрики также работает через stateless REST интерфейс.
 * Это означает, что можно делать параллельные запросы из нескольких потоков, используя один экземпляр {@code MetrikaApi}
 * Каждый полученный отчет потом должен обрабатываться в рамках одного потока, т.к. thread safety библиотек, выгружающих
 * данные из JSON ответов, не гарантируется.
 *
 */
package ru.metrika4j;
