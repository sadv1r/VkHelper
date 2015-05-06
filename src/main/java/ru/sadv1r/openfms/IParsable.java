package ru.sadv1r.openfms;

import java.io.IOException;

/**
 * Базовый, абстрактный класс, для парсеров социальных сетей
 * Created on 5/4/15.
 *
 * @author sadv1r
 * @version 1.0
 */
public interface IParsable {
    /**
     * Парсит данные пользователя из социальной сети
     *
     * @param userIdentifier Уникальный идентификатор пользователя, в социальной сети
     * @return Объект пользователя
     */
    public abstract User parse(String userIdentifier) throws IOException;
}
