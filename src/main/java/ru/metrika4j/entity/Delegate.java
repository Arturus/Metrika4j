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
 * Представитель - аккаунт, которому предоставлен доступ к аккаунту текущего пользователя
 *
 * @author Artur Suilin
 */
public class Delegate {
    private String userLogin;
    private String comment;
    private String createdAt;

    public Delegate() {
    }

    public Delegate(String userLogin, String comment) {
        this.userLogin = userLogin;
        this.comment = comment;
    }

    public Delegate(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public String toString() {
        return "Delegate{" + "userLogin='" + userLogin + '\'' + ", comment='" + comment + '\'' + ", createdAt='" + createdAt + '\'' + '}';
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
