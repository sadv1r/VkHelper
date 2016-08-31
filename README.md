# OpenFMS

Последний билд утилиты:
-----
Не имеет возможности ведения истории!
[OpenFMS-1.0.0-SNAPSHOT](http://file.sadv1r.ru/openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar)

Текущая справка:
-----
```
usage: java -jar openfms-[version].jar [-A | -F | -gu | -H | -i | -m
       <domains>]  [-f <fields>] [-g <domain> | -u <domain>]  -h | -vk
 -A,--all                сохраняем всех пользователей (необходима база)
 -F,--friends            получить список друзей пользователя
 -f,--fields <fields>    установить кастомные поля
                         Например: -а sex,bdate,city
                         Полный список доступных полей:
                         sex,bdate,city,country,photo_max_orig,online,onli
                         ne_mobile,has_mobile,contacts,connections,site,ed
                         ucation,universities,schools,status,last_seen,rel
                         ation,relatives,counters,screen_name,maiden_name,
                         occupation,activities,interests,music,movies,tv,b
                         ooks,games,about,quotes,personal,nickname
 -g,--group <domain>     id или screenName группы
 -gu,--group-users       получить список пользователей группы
 -h,--help               вывод этого сообщения
 -H,--hidden             поиск скрытых друзей
 -i,--info               получить информацию о пользователе
 -m,--fields <domains>   сравнить списки пользователей
 -u,--user <domain>      id или screenName пользователя
 -vk,--vkontakte         работаем с Вконтакте
 ```
Примеры использования:
-----
Для получения справки:
```
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -h
```
Для получения всей информации:
```
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -vk -u <id пользователя или screenName> -i
```
Для получения конкретных полей:
```
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -vk -u <id пользователя или screenName> -i -f sex,bdate
```
Для получения списка скрытых друзей пользователя (находит не всех):
```
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -vk -u <id пользователя или screenName> -H
```
Для получения списка участников группы:
```
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -vk -g <id группы или screenName> -gu
```
Пример комбинированного запроса:
```
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -vk -u <id пользователя или screenName> -F |
java -jar openfms-1.0.0-SNAPSHOT-jar-with-dependencies.jar -vk -i -f sex,bdate
```

Реализация
-----
[Схема базы данных (возможный варинт)](http://dbdesigner.net/designer/schema/808)

[Схема базы данных (текущий варинт)](http://img.sadv1r.ru/8x3ax.png)
