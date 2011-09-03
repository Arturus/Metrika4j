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
 * Абстрактный API для работы с объектами-сущностями  ({@link Goal целями}, {@link Filter фильтрами},
 * {@link Grant правами доступа},
 * {@link Operation операциями})
 *
 * @author Artur Suilin
 */
public interface EntityApi<T extends Entity<K>, K> {
    /** Получить все объекты-сущности для заданного счетчика */
    T[] getEntities(int counterId);

    /** Получить все объекты-сущность с заданным идентификатором для заданного счетчика */
    T getEntity(int counterId, K entityId);

    /**
     * Создает объект-сущность на базе заданного образца (т.е. на вход дается объект-сущность с заполненным полями,
     * Метрика создаёт
     * такой же)
     *
     * @param counterId Идентификатор счетчика
     * @param source    Образец, описывающий сущность.
     * @return Созданная сущность, загруженная из Метрики. У неё должны быть заполнены все поля, заданные в образце,
     *         плюс Метрика
     *         может проставить значения некоторых полей автоматически
     */
    T createEntity(int counterId, T source);

    /**
     * Изменяет объект-сущность в Метрике, основываясь на заданном образце.
     *
     * @param counterId Идентификатор счетчика
     * @param entity    Образец, описывающий сущность.
     * @return Измененная сущность, загруженная из Метрики. У неё должны быть заполнены все поля, заданные в образце,
     *         плюс Метрика
     *         может проставить значения некоторых полей автоматически
     */
    T updateEntity(int counterId, T entity);

    /**
     * Удаляет из Метрики объект-сущность
     *
     * @param counterId Идентификатор счетчика
     * @param entityId  Идентификатор сущности
     */
    void deleteEntity(int counterId, K entityId);
}
