Metrika4j -  Java OOP wrapper for Yandex.Metrika API
================================================
Позволяет работать с [API Яндекс.Метрики](http://api.yandex.ru/metrika/doc/ref/concepts/About.xml) в ООП стиле, используя стандартные для
Java идиомы и не вникая в низкоуровневые детали обработки HTTP запросов и с XML/JSON.

Поддерживаемые платформы:

- JDK 5.0 и выше
- Android API Level 3 и выше


Зависимости: нет


Необязательные зависимости (библиотеки для работы с JSON):

- [Jackson](http://jackson.codehaus.org) JSON processor 1.8 и выше
- [org.json](http://www.json.org/java/index.html) library


Документация: http://arturus.github.com/Metrika4j/apidocs

Общие принципы работы
---------------------
Metrika4j использует публично доступный [REST-интерфейс](<a href="http://api.yandex.ru/metrika/doc/ref/concepts/About.xml)
 к API Яндекс.Метрики. В качестве транспортного формата используется JSON.

Перед началом использования Metrika4j необходимо:

1. [Зарегистрировать приложение](http://api.yandex.ru/oauth/doc/dg/tasks/register-client.xml).
2. [Получить OAuth токен](http://api.yandex.ru/metrika/doc/ref/concepts/authorization.xml). Metrika4j не содержит
никаких средств авторизации - пользователь должен сам получить токен доступными ему способами и передать его в Metrika4j.

Структура методов Metrika4j примерно соответствует структуре REST-вызовов API Метрики (один метод = один вызов API).
Работа со счетчиками и отчетами осуществляется через центральный класс MetrikaApi, работа с дополнительными сущностями
(цели, фильтры и т.п.) - через дополнительные mini-API.

Более подробное описание работы с Metrika4j см. в [документации](http://arturus.github.com/Metrika4j/apidocs/ru/metrika4j/package-summary.html#package_description).




