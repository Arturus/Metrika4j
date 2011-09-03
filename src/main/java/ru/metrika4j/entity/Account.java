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

package ru.metrika4j.entity;

/**
 * Аккаунт, представителем которого является текущий пользователь
 *
 * @author Artur Suilin
 * @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/get-accounts.xml">Справочник API</a>
 */
public class Account {
    private String userLogin;
    private String createdAt;

    public Account(String userLogin) {
        this.userLogin = userLogin;
    }

    public Account() {
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Account{" + "userLogin='" + userLogin + '\'' + ", createdAt='" + createdAt + '\'' + '}';
    }
}
