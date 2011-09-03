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

package ru.metrika4j.io;

/**
 * Интерфейс HTTP транспорта. Возможны реализации  этого интерфейса, использующие разные HTTP библиотеки (HTTP
 * клиент, встроенный в JDK, Apache HTTP клиент и т.п.).
 * В транспорте нет функционала,, отвечающего за авторизацию: предполагается,
 * что транспорту заранее передан OAuth-токен
 * пользователя, от имени которого происходят обращения к API
 */
public interface HttpTransport {
    /**
     * Отправляет HTTP запрос по заданному адресу и возвращает ответ в виде строки. Предполагается,
     * что все коммуникации происходят
     * в кодировке UTF-8.<p>
     * Транспорт должен выполнить следующие дополнительные действия при отправке запроса:
     * <ul>
     * <li>Выставить заголовку "Authorization" значение "OAuth [oAuthToken]", где [oAuthToken] - известный транспорту
     * токен
     * пользователя, от имени которого происходят обращения к API </li>
     * <li>Выставить заголовку "Accept" значение "application/json"</li>
     * <li>Если у запроса есть тело, выставить заголовку "Content-Type" значение "application/json"</li>
     * </ul>
     * <p>Если в ходе работы транспорта возникла ошибка, он должен бросить {@link ru.metrika4j.error
     * .TransportException}.
     * Если ошибка относится к авторизации - бросить {@link ru.metrika4j.error.AuthException}</p>
     *
     * @param url     Адрес, по которому отправляется запрос
     * @param method  HTTP метод
     * @param content Тело запроса (только для PUT и POST запросов). Может быть null.
     * @return текстовый ответ от HTTP сервера
     */
    String doRequest(String url, HttpMethod method, String content);

}
