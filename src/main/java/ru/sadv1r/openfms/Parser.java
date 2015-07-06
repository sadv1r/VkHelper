package ru.sadv1r.openfms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
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
     *
     * @param url Адрес сетевого ресурса
     * @return JsonNode объект
     */
    protected static JsonNode getJsonNodeFromApi(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readTree(new URL(url));
    }
}
