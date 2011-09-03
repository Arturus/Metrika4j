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

import ru.metrika4j.entity.Grant;

/**
 * API для работы с правами доступа
 *
 * @author Artur Suilin
 */
public interface GrantApi {
    /** @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/get-counter-grant-list.xml">Справочник API</a> */
    Grant[] getGrants(int counterId);

    /** @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/get-counter-grant.xml">Справочник API</a> */
    Grant getGrant(int counterId, String userLogin);

    /** @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/add-counter-grant.xml">Справочник API</a> */
    Grant createGrant(int counterId, Grant.Permission permission, String userLogin, String comment);

    /** @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/edit-counter-grant.xml">Справочник API</a> */
    Grant changeGrant(int counterId, Grant.Permission permission, String userLogin, String comment,
                      boolean removeComment);

    /** @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/del-counter-grant.xml">Справочник API</a> */
    void deleteGrant(int counterId, String userLogin);
}
