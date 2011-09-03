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

package ru.metrika4j.error;


/** Ошибка, возникшая при обработке запроса сервером API Метрики */
public class ServerException extends RuntimeException {
    private final MetrikaError[] errors;

    public ServerException(String src, MetrikaError[] errors) {
        super(src);
        this.errors = errors;
    }


    /** Список ошибок, которые вернул сервер Метрики */
    public MetrikaError[] getErrors() {
        return errors;
    }
}
