package ru.sadv1r.openfms;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Основной класс для создания наследующихся классов баз данных
 * Created on 5/8/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public abstract class DataBase {
    /**
     * Получает id пользователей Вконтакте, для последующего парсинга
     * с заданным смещением
     *
     * @param offset Смещение, необходимое для выборки определенного подмножества записей
     * @return id пользователей
     * @throws SQLException
     */
    public abstract ArrayList<Integer> getVkIdsToParse(int offset) throws SQLException;
}
