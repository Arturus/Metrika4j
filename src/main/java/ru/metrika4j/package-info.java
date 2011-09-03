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
 * <p>Java-wrapper для API Яндекс.Метрики</p>
 * Позволяет работать с API Яндекс.Метрики на высоком уровне, не вникая в детали работы с HTTP запросами и с XML/JSON.
 * Поддерживаемые платформы:
 * JDK 5.0 и выше
 * Android API Level 3 и выше
 * * <h3>Общие принципы работы Metrika4j</h3>
 * Java API использует публично доступный REST-интерфейс к API  Яндекс.Метрики, описанный
 * <a href="http://api.yandex.ru/metrika/doc/ref/concepts/About.xml">здесь</a>.
 * В качестве транспортного формата используется JSON.
 *
 *
 * Центральный класс - {@link ru.metrika4j.MetrikaApi}. В нем есть методы для работы с главными сущностями Метрики:
 * счетчиками
 * и отчетами. Работа с дополнительными сущностями (Цели, Фильтры, etc) вынесена в отельные мини-API,
 * получаемые из главного
 * класса {@code MetrikaApi} c помощью методов {@code getXxxApi()}.<br>
 * Структура API примерно соответствует структуре REST-вызовов. Один вид REST-вызова = один метод
 * в API. Аргументы, передаваемые в REST-вызов, соответствуют аргументам, передаваемым в методы API (кроме API работы
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
 * Можно подключить любой другой JSON процессор, имплементировав c его помощью интерфейсы {@link ru.metrika4j.json
 * .JsonMapper}
 * и {@link ru.metrika4j.json.JsonObject}<br>
 *
 * <h3>Начало работы с Metrika4j</h3>
 * Сначала необходимо создать экземпляр {@link MetrikaApi} c помощью {@link ApiFactory}
 * <pre>
 *    // Создаем экземпляр API, использующий Jackson JSON-processor и транспорт по умолчанию
 *    MetrikaApi api = ApiFactory.createMetrikaAPI("05dd3dd84ff948fdae2bc4fb91f13e22", new JacksonMapper());
 * </pre>
 * Если HTTP транспорт по умолчанию (использующий стандартный {@code HttpUrlConnection} из JDK) не устраивает,
 * можно создать свой транспорт, имплементировав  {@link ru.metrika4j.io.HttpTransport}. Транспорт должен получать
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
 * </pre>
 * При повышении детализации будет увеличиваться время ответа и объем передаваемой информации, поэтому лучше
 * не злоупотреблять высокой детализацией.
 *
 */
package ru.metrika4j;
