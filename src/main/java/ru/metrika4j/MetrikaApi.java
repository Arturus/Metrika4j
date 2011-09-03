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

import ru.metrika4j.entity.Counter;
import ru.metrika4j.entity.CounterDetails;

/**
 * API Яндекс.Метрики.<br>
 * Непосредственно в этом интерфейсе есть методы для работы со счетчиками (создание, удаление,
 * etc) и отчетами. Для работы с
 * другими сущностями (фильтры, цели, etc), надо получить интерфейс для работы с этим видом сущностей, с помощью метода
 * getXxxApi, например для целей  - метод {@link #getGoalApi()} .
 * <p>Экземпляр API для использования в приложении создаётся с помощью {@link ApiFactory}</p>
 *
 * @author Artur Suilin
 */
public interface MetrikaApi {
    /**
     * Возвращает список всех счетчиков, доступных в текущем аккаунте Я.Метрики
     *
     * @param details Требуемая детализация данных каждого счетчика
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/get-counter-list.xml">Справочник API</a>
     */
    Counter[] getCounters(CounterDetails... details);

    /**
     * Возвращает счетчик по его идентификатору
     *
     * @param details Требуемая детализация данных счетчика
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/get-counter.xml">Справочник API</a>
     */
    Counter getCounter(int id, CounterDetails... details);

    /**
     * Возвращает построитель для заданного отчёта
     *
     * @param report    Отчёт
     * @param counterId идентификатор счетчика
     */
    ReportBuilder makeReportBuilder(Reports report, int counterId);

    /**
     * Создаёт новый счетчик в аккаунте текущего пользователя
     *
     * @param newCounter Объект с заполненными полями, представляющий новый счетчик
     * @return Созданный счетчик. Это объект с теми же полями, что у входного newCounter,
     *         плюс Метрика может сама проставить
     *         значения некоторых полей по умолчанию.
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/add-counter.xml">Справочник API</a>
     */
    Counter createCounter(Counter newCounter);

    /**
     * Изменяет счетчик в аккаунте текущего пользователя
     *
     * @param counter Объект с заполненными полями, представляющий счетчик, который должен быть  изменён
     * @return Измененный счетчик. Это объект с теми же полями, что у входного счетчика,
     *         плюс Метрика может сама проставить
     *         значения некоторых полей по умолчанию.
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/edit-counter.xml">Справочник API</a>
     */
    Counter updateCounter(Counter counter);

    /**
     * Удаляет счетчик с заданным идентификатором
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/reference/delete-counter.xml">Справочник API</a>
     */
    void deleteCounter(int counterId);


    GoalApi getGoalApi();

    FilterApi getFilterApi();

    OperationApi getOperationApi();

    GrantApi getGrantApi();

    DelegateApi getDelegateApi();

    AccountApi getAccountApi();


}
