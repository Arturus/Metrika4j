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
 * @author Artur Suilin
 * @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/get-counter-grant.xml">Разрешение на работу со
 *      счетчиком</a>
 */
public class Grant implements Entity<String> {
    private String userLogin;
    private Permission perm;
    private String createdAt;
    private String comment;

    public Grant() {
    }

    public Grant(String userLogin, Permission perm, String comment) {
        this.userLogin = userLogin;
        this.perm = perm;
        this.comment = comment;
    }

    public Grant(String userLogin, Permission perm) {
        this.userLogin = userLogin;
        this.perm = perm;
    }

    @Override
    public String toString() {
        return "Grant{" + "userLogin='" + userLogin + '\'' + ", perm=" + perm + ", createdAt='" + createdAt + '\'' + ", comment='" + comment + '\'' + '}';
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

    public Permission getPerm() {
        return perm;
    }

    public void setPerm(Permission perm) {
        this.perm = perm;
    }

    public String getId() {
        return userLogin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grant)) {
            return false;
        }

        Grant grant = (Grant) o;

        return !(comment != null ? !comment.equals(
                grant.comment) : grant.comment != null) && !(createdAt != null ? !createdAt.equals(
                grant.createdAt) : grant.createdAt != null) && perm == grant.perm && !(userLogin != null ? !userLogin.equals(
                grant.userLogin) : grant.userLogin != null);

    }

    @Override
    public int hashCode() {
        int result = userLogin != null ? userLogin.hashCode() : 0;
        result = 31 * result + (perm != null ? perm.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    public enum Permission {
        public_stat,
        view,
        edit,

    }

}
