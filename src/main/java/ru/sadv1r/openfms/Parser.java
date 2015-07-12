package ru.sadv1r.openfms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;

/**
 * Created on 6/24/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public abstract class Parser {
    /**
     * Получает ответ ресурса в формате Json по url
     * <p/>
     * Если ловим ConnectException, пробуем посылать запросы повторно.
     * Первый повторный запрос посылается без задержки, затем время задержки увеличивается
     * <p/>
     *
     * @param stringUrl Адрес сетевого ресурса
     * @return JsonNode объект
     */
    protected static JsonNode getJsonNodeFromApi(String stringUrl) throws IOException {
        final int numberOfReconnectionAttempts = 3;
        final int millisMultiplier = 2;
        long waitingMillisBetweenAttempts = 500;

        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL(stringUrl);
        double maximumWaitingMillisBetweenAttempts = waitingMillisBetweenAttempts * (Math.pow(millisMultiplier, numberOfReconnectionAttempts - 1));

        try {
            return mapper.readTree(url);
        } catch (ConnectException e) {
            while (waitingMillisBetweenAttempts <= maximumWaitingMillisBetweenAttempts) {
                try {
                    return mapper.readTree(url);
                } catch (ConnectException e1) {
                    waitingMillisBetweenAttempts *= millisMultiplier;
                }

                try {
                    Thread.sleep(waitingMillisBetweenAttempts);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
            throw e;
        }
    }
}
