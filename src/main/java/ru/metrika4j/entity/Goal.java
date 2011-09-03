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


/** @see <a href=" http://api.yandex.ru/metrika/doc/ref/reference/get-counter-goal.xml">Цель</a> */
public class Goal implements Entity<Integer> {
    private int id;
    private String name;
    private int depth;
    private Condition[] conditions;

    //private YandexMarketType flag;
    private GoalType type;

    public static Goal createDepthGoal(String name, int depth) {
        Goal result = new Goal();
        result.name = name;
        result.depth = depth;
        result.type = GoalType.number;
        return result;
    }


    public static Goal createUrlGoal(String name, Condition[] conditions) {
        Goal result = new Goal();
        result.name = name;
        result.conditions = conditions;
        result.type = GoalType.url;
        return result;
    }

    public static Goal createUrlGoal(String name, ConditionType type, String url) {
        Condition[] conditions = new Condition[]{new Condition(url, type)};
        return createUrlGoal(name, conditions);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public GoalType getType() {
        return type;
    }

    public void setType(GoalType type) {
        this.type = type;
    }

//    @JsonIgnore //TODO сделать десериализатор, обрабатывающий пустую строку. DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT?
//    public YandexMarketType getFlag() {
//        return flag;
//    }
//
//    public void setFlag(YandexMarketType flag) {
//        this.flag = flag;
//    }

    public Condition[] getConditions() {
        return conditions;
    }

    public void setConditions(Condition[] conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Goal)) {
            return false;
        }

        Goal goal = (Goal) o;

        return depth == goal.depth && id == goal.id && Arrays.equals(conditions,
                goal.conditions) && !(name != null ? !name.equals(goal.name) : goal.name != null) && type == goal.type;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + depth;
        result = 31 * result + (conditions != null ? Arrays.hashCode(conditions) : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Goal{" + "id=" + id + ", name='" + name + '\'' + ", depth=" + depth + ", conditions=" + (conditions == null ? null : Arrays
                .asList(conditions)) + ", type=" + type + '}';
    }

    public Goal() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static class Condition {
        private String url;
        private ConditionType type;

        public Condition() {
        }

        public Condition(String url, ConditionType type) {
            this.url = url;
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public ConditionType getType() {
            return type;
        }

        public void setType(ConditionType type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Condition)) {
                return false;
            }

            Condition condition = (Condition) o;

            return type == condition.type && !(url != null ? !url.equals(condition.url) : condition.url != null);

        }

        @Override
        public int hashCode() {
            int result = url != null ? url.hashCode() : 0;
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }
    }

    public static enum ConditionType {
        contain,
        exact,
        start
    }

//    public static enum YandexMarketType {
//        basket,
//        order
//    }

    public static enum GoalType {
        url,
        number
    }


}
