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
 * <p>Построитель отчетов</p>.
 * Позволяет указать входные параметры отчетов. Набор возможных параметров указан в описании
 * каждого отчета. Для указания часто используемых параметров (временной интервал, сортировка и т.п.), существуют
 * специальные методы. <br>
 * Если требуется входной параметр отчета, для установки которого не существует соответствующего метода,
 * можно воспользоваться функцией ({@link #withParameter(String, String)}), позволяющей установить параметр с
 * произвольным именем.
 * <p>Для построения отчета после задания параметров надо вызвать метод {@link #build()}</p>
 * <p>Экземпляр построителя отчётов получается путём вызова {@link MetrikaApi#makeReportBuilder(Reports, int)}  </p>
 * <p>Один и тот же построитель отчетов может использоваться несколько раз, с разными параметрами. Для установки
 * нового значения параметра передайте значение в соответствующий метод {@code withXXX()}, для сброса параметра
 * передайте в качестве значения {@code null}</p>
 *
 * @author Artur Suilin
 */
public interface ReportBuilder {

    /**
     * Задаёт нижнюю границу временного интервала, по которому будет построен отчёт. Если границы не заданы, будет
     * построен отчёт за последнюю неделю
     *
     * @see #withDateTo(MetrikaDate)
     */
    ReportBuilder withDateFrom(MetrikaDate date);

    /**
     * Задаёт верхнюю границу временного интервала, по которому будет построен отчёт. Если границы не заданы, будет
     * построен отчёт за последнюю неделю
     *
     * @see #withDateFrom(MetrikaDate)
     */
    ReportBuilder withDateTo(MetrikaDate date);

    /**
     * Задаёт идентификатор цели, для получения целевого отчёта.
     *
     * @see ru.metrika4j.entity.Goal
     */
    ReportBuilder withGoal(Integer goalId);

    /** Задаёт группировку данных по времени. Если группировка не задана, по умолчанию данные группируются по дням */
    ReportBuilder groupBy(Group group);

    /**
     * Задаёт сортировку по возрастанию по произвольному полю отчёта
     *
     * @param field имя поля в отчете (имена полей перечислены в документации на каждый отчёт)
     * @see #reverseSortBy(String)
     */
    ReportBuilder sortBy(String field);

    /**
     * Задаёт сортировку по убыванию по произвольному полю отчёта
     *
     * @param field имя поля в отчете (имена полей перечислены в документации на каждый отчёт)
     * @see #sortBy(String)
     */
    ReportBuilder reverseSortBy(String field);


    /**
     * Задаёт представление результатов отчета - в виде таблиц или в виде дерева. По умолчанию результаты
     * представляются
     * в виде таблицы.
     */
    ReportBuilder withTableMode(TableMode tm);

    /**
     * Задаёт фильтрацию по указанному зеркалу сайта. Применяется для отчетов по содержанию.
     *
     * @param mirrorId числовой идентификатор зеркала, 0 - основной сайт; 1 и более - порядковый номер из массива
     *                 зеркал (см. параметр mirrors в описании счётчика);
     *                 отрицательное число - будут выведены данные для сайтов и адресов,
     *                 не указанных в настройках счётчика.
     */
    ReportBuilder withMirrorId(Integer mirrorId);

    /**
     * Задаёт входной параметр отчёта с произвольным именем.
     *
     * @param name  Имя параметра
     * @param value Значение параметра
     */
    ReportBuilder withParameter(String name, String value);

    /** Задаёт количество записей на одну "страницу" отчёта */
    ReportBuilder withItemsPerPage(Integer numberOfItems);

    /**
     * Выполняет фактическое построение отчёта. Функция в ходе работы обращается за данными к серверам Яндекс
     * .Метрики.
     *
     * @throws ru.metrika4j.error.NoDataException
     *
     */
    Report build();


    /** Способ группировки данных по времени в отчёте */
    public enum Group {
        day,
        week,
        month
    }

    /** Способ представления результатов отчёта */
    public enum TableMode {
        /** Результаты в виде дерева */
        tree,
        /** Результаты в виде таблицы */
        plain
    }


}
