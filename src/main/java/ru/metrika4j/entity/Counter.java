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

import java.util.Arrays;

/**
 * Cчётчик.<p>
 *
 * @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/get-counter.xml">Поля счетчика</a>
 */
public class Counter implements Entity<Integer> {
    private int id;
    private String ownerLogin;
    private String code;
    private CodeOptions codeOptions;
    private Status codeStatus;
    private String name;
    private String site;
    private CounterType type;
    private Permission permission;
    private Monitoring monitoring;

    // Необязательные параметры
    private String[] mirrors;
    private Goal[] goals;
    private Filter[] filters;
    private Operation[] operations;
    private Grant[] grants;

    public Counter() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CodeOptions getCodeOptions() {
        return codeOptions;
    }

    public void setCodeOptions(CodeOptions codeOptions) {
        this.codeOptions = codeOptions;
    }

    public Status getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(Status codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public CounterType getType() {
        return type;
    }

    public void setType(CounterType type) {
        this.type = type;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Monitoring getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(Monitoring monitoring) {
        this.monitoring = monitoring;
    }

    public String[] getMirrors() {
        return mirrors;
    }

    public void setMirrors(String[] mirrors) {
        this.mirrors = mirrors;
    }

    public Goal[] getGoals() {
        return goals;
    }

    public void setGoals(Goal[] goals) {
        this.goals = goals;
    }

    public Filter[] getFilters() {
        return filters;
    }

    public void setFilters(Filter[] filters) {
        this.filters = filters;
    }

    public Operation[] getOperations() {
        return operations;
    }

    public void setOperations(Operation[] operations) {
        this.operations = operations;
    }

    public Grant[] getGrants() {
        return grants;
    }

    public void setGrants(Grant[] grants) {
        this.grants = grants;
    }

    @Override
    public String toString() {
        return "Counter{" + "id=" + id + ", ownerLogin='" + ownerLogin + '\'' + ", codeOptions=" + codeOptions + ", codeStatus=" + codeStatus + ", name='" + name + '\'' + ", site='" + site + '\'' + ", type=" + type + ", permission=" + permission + ", monitoring=" + monitoring + ", mirrors=" + (mirrors == null ? null : Arrays
                .asList(mirrors)) + ", goals=" + (goals == null ? null : Arrays.asList(
                goals)) + ", filters=" + (filters == null ? null : Arrays.asList(
                filters)) + ", operations=" + (operations == null ? null : Arrays.asList(
                operations)) + ", grants=" + (grants == null ? null : Arrays.asList(grants)) + '}';
    }


    public static enum Status {
        CS_ERR_CONNECT,    //Не удалось проверить (ошибка соединения).
        CS_ERR_DUPLICATED,    //Установлен более одного раза.
        CS_ERR_HTML_CODE,    //Установлен некорректно.
        CS_ERR_OTHER_HTML_CODE,    //Уже установлен другой счётчик.
        CS_ERR_TIMEOUT,    //Не удалось проверить (превышено время ожидания).
        CS_ERR_UNKNOWN,    //Неизвестная ошибка.
        CS_NEW_COUNTER,    //Недавно создан.
        CS_NOT_EVERYWHERE,    //Установлен не на всех страницах.
        CS_NOT_FOUND,    //Не установлен.
        CS_NOT_FOUND_HOME,    //Не установлен на главной странице.
        CS_NOT_FOUND_HOME_LOAD_DATA,    //Не установлен на главной странице, но данные поступают.
        CS_OK,    //Корректно установлен.
        CS_OK_NO_DATA,    //Установлен, но данные не поступают.
        CS_WAIT_FOR_CHECKING,    //Ожидает проверки наличия.
        CS_WAIT_FOR_CHECKING_LOAD_DATA,    //Ожидает проверки наличия, данные поступают.
        CS_OBSOLETE
    }

    public static class Monitoring {
        public boolean enableMonitoring;
        public String[] emails;
        public boolean smsAllowed;
        public boolean enableSms;
        public String smsTime;

        @Override
        public String toString() {
            return "Monitoring{" + "enableMonitoring=" + enableMonitoring + ", emails=" + (emails == null ? null : Arrays
                    .asList(emails)) + ", smsAllowed=" + smsAllowed + ", enableSms=" + enableSms + ", smsTime='" + smsTime + '\'' + '}';
        }
    }

    public static class CodeOptions {
        public boolean clickmap;
        public boolean externalLinks;
        public boolean async;
        public Informer informer;
        public boolean visor;
        public boolean params;
        public boolean denial;
        public boolean trackHash;
        public boolean ut;


    }

    public static class Informer {
        public boolean enabled;
        public HtmlType type;
        public int size;
        public Indicator indicator;
        public String colorStart;
        public String colorEnd;
        public int colorText;
        public int colorArrow;
        public boolean allowed;


        /** Тип HTML-кода счетчика. */
        public static enum HtmlType {
            /** счетчик без информера (по умолчанию); */
            counter,

            /** счётчик с информером */
            together,

            /** только информер */
            informer,
            ext
        }

        public static enum Indicator {
            /** просмотры (по умолчанию); */
            pageviews,

            /** визиты */
            visits,

            /** посетители */
            uniques
        }


    }

    /** Тип счётчика */
    public static enum CounterType {
        /** cчётчик создан пользователем в Метрике */
        simple,
        /** счётчик импортирован из РСЯ */
        partner
    }

    /** Уровень доступа к счётчику */
    public static enum Permission {
        /** собственный счётчик пользователя */
        own,
        /** гостевой счётчик с уровнем доступа «только просмотр» */
        view,
        /** гостевой счётчик с уровнем доступа «полный доступ» */
        edit
    }

}
