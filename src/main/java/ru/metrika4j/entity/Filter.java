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

/** @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/get-counter-filter.xml">Фильтр.</a> */
public class Filter implements Entity<Integer> {
    private int id;
    private String value;
    private Action action;
    private Attr attr;
    private Type type;
    private Status status;

    public static Filter createFilter(Attr attr, Type type, String value, Action action) {
        Filter result = new Filter();
        result.attr = attr;
        result.type = type;
        result.value = value;
        result.action = action;
        result.status = Status.active;
        return result;
    }

    public static Filter createOnlyMirrorsFilter() {
        Filter result = new Filter();
        result.attr = Attr.url;
        result.type = Type.only_mirrors;
        result.action = Action.include;
        result.status = Status.active;
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Action {
        include,
        exclude
    }

    public enum Attr {
        client_ip,
        referer,
        url,
        title,
        uniq_id
    }

    public enum Type {
        contain,
        start,
        equal,
        interval,
        only_mirrors,
        me
    }

    public enum Status {
        active,
        disabled
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filter)) {
            return false;
        }

        Filter filter = (Filter) o;

        return id == filter.id && action == filter.action && attr == filter.attr && status == filter.status && type == filter.type && !(value != null ? !value
                .equals(filter.value) : filter.value != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (attr != null ? attr.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Filter{" + "id=" + id + ", value='" + value + '\'' + ", action=" + action + ", attr=" + attr + ", type=" + type + ", status=" + status + '}';
    }
}
