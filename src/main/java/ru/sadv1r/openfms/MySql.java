package ru.sadv1r.openfms;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Класс для работы с MySQL
 * Created on 5/8/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public class MySql extends DataBase {
    private static final int GET_VK_ID_LIST_LIMIT = 1000;
    private static final Logger LOGGER = Logger.getLogger(MySql.class);
    private Connection connection = null;
    private static final String CHARSET = "UTF-8";

    public MySql(String serverName, int portNumber, String userName, String password, String database) throws SQLException {
        LOGGER.debug("Конструктор запущен");
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + portNumber + "/" + database +
                    "?characterEncoding=" + CHARSET, connectionProps);
            LOGGER.trace("Подключение: " + connection);
            LOGGER.info("Подключение получено");
        } catch (SQLException e) {
            LOGGER.error(e);
            throw e;
        }
        LOGGER.debug("Конструктор завершил работу");
    }

    @Override
    public ArrayList<Integer> getVkIdsToParse(int offset) throws SQLException {
        LOGGER.debug("Метод getVkIdsToParse запущен");
        ArrayList<Integer> vkIds = new ArrayList<>(GET_VK_ID_LIST_LIMIT);
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT vk_id FROM vk_id_list ORDER BY id DESC LIMIT " +
                    GET_VK_ID_LIST_LIMIT + " OFFSET " + offset);
            LOGGER.trace("Запрос \"SELECT vk_id FROM vk_id_list ORDER BY id DESC LIMIT " + GET_VK_ID_LIST_LIMIT +
                    " OFFSET " + offset + "\" прошел успешно");

            while (resultSet.next()) {
                vkIds.add(resultSet.getInt(1));
            }
            LOGGER.info("Получено id: " + vkIds.size());
            LOGGER.debug("Метод getVkIdsToParse завершил работу");
            return vkIds;
        } catch (SQLException e) {
            LOGGER.error(e);
            throw e;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.error(e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.error(e);
                }
            }
        }
    }

    public void close() throws SQLException {
        connection.close();
    }

}
