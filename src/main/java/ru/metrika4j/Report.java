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
 * Отчёт системы Яндекс.Метрика. Генерируется при вызове метода {@link ReportBuilder#build()}.
 * Табличный отчёт содержит массив основных результатов ({@link #getData()}}), а также может содержать дополнительные
 * результаты, для извлечения которых используется функция {@link #getArray(String)}.
 * <br> Содержимое записей в массиве результатов описано в документации на каждый отчёт
 * <p>Если в отчете много данных, он может состоять из нескольких "страниц". При создании отчёта загружается его первая
 * страница, для получения следующих страниц необходимо пользоваться методами {@link #hasNextPage()} и {@link
 * #getNextPage()}</p>
 *
 * @author Artur Suilin
 */
public interface Report {

    /**
     * Возвращает true, если есть следующая "страница" данных отчёта. Для перехода к следующей странице
     * используется метод {@link #getNextPage()}
     */
    boolean hasNextPage();

    /** Возвращает идентификатор счётчика, для которого построен отчёт */
    int getCounterId();

    /**
     * Возвращает идентификатор цели для целевого отчёта, или null для нецелевого отчёта
     *
     * @see ReportBuilder#withGoal(int)
     */
    Integer getGoalId();

    /**
     * Возвращает начало интервала времени, за который построен отчёт
     *
     * @see ReportBuilder#withDateFrom(MetrikaDate)
     */
    MetrikaDate getDateFrom();

    /**
     * Возвращает конец интервала времени, за который построен отчёт
     *
     * @see ReportBuilder#withDateTo(MetrikaDate)
     */
    MetrikaDate getDateTo();

    /** Возвращает массив основных результатов отчёта (соответствует параметру <b>data</b> в выходных данных отчёта) */
    ReportItem[] getData();

    /**
     * Возвращает массив с дополнительными результатами отчёта
     *
     * @param name имя параметра, содержащего дополнительные результаты
     */
    ReportItem[] getArray(String name);

    /**
     * Итоговые данные отчёта
     *
     * @return Структура с итоговыми данными (обычно она повторяет структуру строки отчёта) или null, если в отчёте
     *         нет итоговых данных
     */
    ReportItem getTotals();

    /** Возвращает "следующую страницу" отчёта, если она существует, или null */
    Report getNextPage();

}
