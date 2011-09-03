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

import ru.metrika4j.entity.Account;

/**
 * API для работы с аккаунтами, представителем которых является текущий пользователь
 *
 * @author Artur Suilin
 */
public interface AccountApi {
    /** @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/get-accounts.xml">Справочник API</a> */
    Account[] getAccounts();

    /** @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/del-account.xml">Справочник API</a> */
    void deleteAccount(String user_login);

    /** @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/edit-accounts.xml">Справочник API</a> */
    Account[] updateAccounts(Account[] accountList);
}
