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
 * Отчёты, доступные в API Яндекс.Метрики
 *
 * @author Artur Suilin
 */
public enum Reports {


    /**
     * Отчёт Трафик -> Посещаемость.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/traffic-summary.xml">Справочник API</a>
     */
    trafficSummary("/stat/traffic/summary"),

    /**
     * Отчёт Трафик -> По времени суток.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/traffic-hourly.xml">Справочник API</a>
     */
    trafficHourly("/stat/traffic/hourly"),

    /**
     * Отчёт Трафик -> Вовлечение.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/traffic-deepness.xml">Справочник API</a>
     */
    trafficDeepness("/stat/traffic/deepness"),

    /**
     * Отчёт Трафик -> Нагрузка на сайт.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/traffic-load.xml">Справочник API</a>
     */
    trafficLoad("/stat/traffic/load"),

    /**
     * Отчёт Источники -> Сводка.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/sources-summary.xml">Справочник API</a>
     */
    sourcesSummary("/stat/sources/summary"),


    /**
     * Отчёт Источники -> Сайты.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/sources-sites.xml">Справочник API</a>
     */
    sourcesSites("/stat/sources/sites"),

    /**
     * Отчёт Источники -> Поисковые системы.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/sources-search-engines.xml">Справочник API</a>
     */
    sourcesSearchEngines("/stat/sources/search_engines"),

    /**
     * Отчёт Источники -> Поисковые фразы.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/sources-phrases.xml">Справочник API</a>
     */
    sourcesPhrases("/stat/sources/phrases"),

    /**
     * Отчёт Источники -> Рекламные системы.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/sources-marketing.xml">Справочник API</a>
     */
    sourcesMarketing("/stat/sources/marketing"),

    /**
     * Отчёт Источники -> Директ -> сводка.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/sources-direct-stats.xml">Справочник API</a>
     */
    sourcesDirectSummary("/stat/sources/direct/summary"),

    /**
     * Отчёт Источники -> Директ -> площадки.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/sources-direct-platforms.xml">Справочник API</a>
     */
    sourcesDirectPlatforms("/stat/sources/direct/platforms"),

    /**
     * Отчёт Источники -> Директ -> регионы.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/sources-direct-regions.xml">Справочник API</a>
     */
    sourcesDirectRegions("/stat/sources/direct/regions"),

    /**
     * Отчёт Источники -> Метки.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/sources-tags.xml">Справочник API</a>
     */
    sourcesTags("/stat/sources/tags"),

    /**
     * Отчёт Содержание -> Популярное.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/content-popular.xml">Справочник API</a>
     */
    contentPopular("/stat/content/popular"),

    /**
     * Отчёт Содержание -> Страницы входа.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/content-entrance.xml">Справочник API</a>
     */
    contentEntrance("/stat/content/entrance"),

    /**
     * Отчёт Содержание -> Страницы выхода.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/content-exit.xml">Справочник API</a>
     */
    contentExit("/stat/content/exit"),

    /**
     * Отчёт Содержание -> Заголовки.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/content-titles.xml">Справочник API</a>
     */
    contentTitles("/stat/content/titles"),

    /**
     * Отчёт Содержание -> Параметры URL.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/content-url-param.xml">Справочник API</a>
     */
    contentUrlParam("/stat/content/url_param"),

    /**
     * Отчёт по странам мира.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/geo.xml">Справочник API</a>
     */
    geo("/stat/geo"),

    /**
     * Отчёт по полу и возрасту.
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/demography-age-gender.xml">Справочник API</a>
     */
    demographyAgeGender("/stat/demography/age_gender"),

    /**
     * Отчет по половозрастной структуре
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/demography-structure.xml">Справочник API</a>
     */
    demographyStructure("/stat/demography/structure"),

    /**
     * Отчет Компьютеры -> браузеры
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/tech-browsers.xml">Справочник API</a>
     */
    techBrowsers("/stat/tech/browsers"),

    /**
     * Отчет Компьютеры -> Операционные системы
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/tech-os.xml">Справочник API</a>
     */
    techOs("/stat/tech/os"),

    /**
     * Отчет Компьютеры -> Разрешения дисплеев
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/tech-display.xml">Справочник API</a>
     */
    techDisplay("/stat/tech/display"),

    /**
     * Отчет Компьютеры -> Мобильные устройства
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/tech-mobile.xml">Справочник API</a>
     */
    techMobile("/stat/tech/mobile"),

    /**
     * Отчет Компьютеры -> Версии Flash
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/tech-flash.xml">Справочник API</a>
     */
    techFlash("/stat/tech/flash"),


    /**
     * Отчет Компьютеры -> Версии Silverlight
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/tech-silverlight.xml">Справочник API</a>
     */
    techSilverlight("/stat/tech/silverlight"),

    /**
     * Отчет Компьютеры -> Версии .NET
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/tech-dotnet.xml">Справочник API</a>
     */
    techDotnet("/stat/tech/dotnet"),


    /**
     * Отчет Компьютеры -> Наличие Java
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/tech-java.xml">Справочник API</a>
     */
    techJava("/stat/tech/java"),

    /**
     * Отчет Компьютеры -> Наличие cookies
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/tech-cookies.xml">Справочник API</a>
     */
    techCookies("/stat/tech/cookies"),

    /**
     * Отчет Компьютеры -> Наличие Javascript
     *
     * @see <a href="http://api.yandex.ru/metrika/doc/ref/stat/tech-javascript.xml">Справочник API</a>
     */
    techJavascript("/stat/tech/javascript");


    public final String url;

    Reports(String url) {
        this.url = url;
    }
}
