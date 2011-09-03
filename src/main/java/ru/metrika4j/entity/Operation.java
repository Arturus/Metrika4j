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
 * @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/get-counter-operation.xml">Операция,
 *      модифицирующая URL страницы</a>
 */
public class Operation implements Entity<Integer> {
    private int id;
    private String value;
    private Action action;
    private Attr attr;
    private Status status;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Operation{" + "id=" + id + ", value='" + value + '\'' + ", action=" + action + ", attr=" + attr + ", " + "status=" + status + '}';
    }

    public static enum Action {
        cut_fragment,
        cut_parameter,
        merge_https_and_http,
        to_lower,
        replace_domain
    }

    public static enum Attr {
        referer,
        url
    }

    public enum Status {
        active,
        disabled
    }


}
