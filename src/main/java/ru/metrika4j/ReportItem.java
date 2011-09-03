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

/**
 * Одна запись с результатами отчёта. Каждая запись состоит из набора именованных полей. Для доступа к содержимому поля
 * используются, в зависимости от типа поля, методы {@link #getInt(String)}, {@link #getString(String)},
 * {@link #getDouble(String)}.
 * <p/>
 * Отчеты могут иметь сложную структуру, со вложенными записями. Для работы с
 * такими отчетами используются методы {@link #getRecord(String)} для получения вложенной структуры
 * и {@link #getArray(String)} для получения вложенного массива.
 * <p/>
 *
 * @author Artur Suilin
 */
public interface ReportItem {

    /**
     * Возвращает содержимое целочисленного поля или null, если поля с таким именем не существует
     *
     * @param field Имя поля
     */
    public Integer getInt(String field);

    /**
     * Возвращает содержимое поля или null, если поля с таким именем не существует
     *
     * @param field Имя поля
     */
    public String getString(String field);

    /**
     * Возвращает содержимое поля, интерпретируемое как число с плавающей точкой, или null,
     * если поля с таким именем не существует
     *
     * @param field Имя поля
     */
    public Double getDouble(String field);

    /**
     * Возвращает содержимое поля со вложенной структурой, или null, если поля с таким именем не существует
     *
     * @param field имя поля
     */
    public ReportItem getRecord(String field);

    /**
     * Возвращает содержимое поля со вложенным массивом, или null, если поля с таким именем не существует
     *
     * @param field имя поля
     */
    public ReportItem[] getArray(String field);

}
