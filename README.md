# OpenFMS

Последний билд утилиты:
-----
Не имеет возможности ведения истории!
[OpenFMS-1.0.0-SNAPSHOT](http://file.sadv1r.ru/openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar)

Использование:
-----
Для получения всей информации:
```
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -vk -domain <id пользователя или screenName>
```
Для получения конкретного поля:
```
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -vk -domain <id пользователя или screenName> -p FirstName
```
Для получения списка скрытых друзей пользователя (находит не всех):
```
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -vk -hidden <id пользователя>
```
Для получения списка участников группы:
```
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -vk -domain -<id группы>
```

Цель:
-----
Получение разнообразной интересной информации из открытых источников

Поддерживаемые социальные сети:
-----
Вконтакте


Реализация
-----
[Схема базы данных (возможный варинт)](http://dbdesigner.net/designer/schema/808)

[Схема базы данных (текущий варинт)](http://img.sadv1r.ru/8x3ax.png)
