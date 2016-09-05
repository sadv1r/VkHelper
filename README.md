# VkHelper

Скачать:
-----
[VkHelper-1.0.0-ALPHA](http://file.sadv1r.ru/vk-helper-1.0.0-ALPHA.jar)

[VkHelper-1.0.0-SNAPSHOT](http://file.sadv1r.ru/vk-helper-1.0.0-SNAPSHOT-jar-with-dependencies.jar)

Текущая справка:
-----
```
usage: java -jar vk-helper-[version].jar -F | -gu | -h | -H | -i | -m
       <domains> [-f <fields>] [-g <domain> | -u <domain>]
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
 -m,--mutual <domains>   сравнить списки пользователей
 -u,--user <domain>      id или screenName пользователя
 ```
Примеры использования:
-----
Для получения справки:
```
java -jar vk-helper-1.0.0-SNAPSHOT-jar-with-dependencies.jar -h
```
Для получения всей информации:
```
java -jar vk-helper-1.0.0-SNAPSHOT-jar-with-dependencies.jar -u <id пользователя или screenName> -i
```
Для получения конкретных полей:
```
java -jar vk-helper-1.0.0-SNAPSHOT-jar-with-dependencies.jar -u <id пользователя или screenName> -i -f sex,bdate
```
Для получения списка скрытых друзей пользователя (находит не всех):
```
java -jar vk-helper-1.0.0-SNAPSHOT-jar-with-dependencies.jar -u <id пользователя или screenName> -H
```
Для получения списка участников группы:
```
java -jar vk-helper-1.0.0-SNAPSHOT-jar-with-dependencies.jar -g <id группы или screenName> -gu
```
Пример комбинированного запроса:
```
java -jar vk-helper-1.0.0-SNAPSHOT-jar-with-dependencies.jar -u <id пользователя или screenName> -F |
java -jar vk-helper-1.0.0-SNAPSHOT-jar-with-dependencies.jar -i -f sex,bdate
```
