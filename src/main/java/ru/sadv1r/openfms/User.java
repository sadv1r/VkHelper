package ru.sadv1r.openfms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * Основной класс для создания пользователя социальной сети
 * Created on 5/4/15.
 *
 * @author sadv1r
 * @version 1.0
 */
public abstract class User implements IParsable {
    /**
     * Получает ответ ресурса в формате Json по url
     *
     * @param url Адрес сетевого ресурса
     * @return JsonNode объект
     */
    protected static JsonNode getJsonNodeFromApi(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readTree(new URL(url));
    }
}
