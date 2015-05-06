package ru.sadv1r.openfms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.IOException;

/**
 * Пользователь социальной сети Вконтакте
 * Created on 5/4/15.
 *
 * @author sadv1r
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkUser extends User {
    private static final int VK_MIN_ID = 0;
    private static final int VK_MAX_ID = 1000_000_000;
    private String fieldsToParse = "sex,bdate,city,country,photo_50,photo_100,photo_200_orig,photo_200,photo_400_orig," +
            "photo_max,photo_max_orig,photo_id,online,online_mobile,domain,has_mobile,contacts,connections,site," +
            "education,universities,schools,can_post,can_see_all_posts,can_see_audio,can_write_private_message,status," +
            "last_seen,relation,relatives,counters,screen_name,maiden_name,occupation,activities,interests,music,movies," +
            "tv,books,games,about,quotes,personal,nickname";

    @JsonProperty("id")
    @NotNull
    private int vkId;

    @JsonProperty("first_name")
    @Size(min = 1, max = 20)
    private String firstName;

    @JsonProperty("last_name")
    @Size(min = 1, max = 20)
    private String lastName;

    @JsonProperty("sex")
    private boolean sex;

    @JsonProperty("nickname")
    @Size(min = 1, max = 20)
    private String nickname;

    @JsonProperty("maiden_name")
    @Size(min = 1, max = 20)
    private String maidenName;

    @JsonProperty("screen_name")
    @Size(min = 1, max = 20)
    private String screenName;

    @JsonProperty("bdate")
    @Size(min = 5, max = 10)
    private String birthday;


    public int getVkId() {
        return vkId;
    }

    @JsonSetter("id")
    public void setVkId(int vkId) {
        this.vkId = vkId;
    }

    public String getFirstName() {
        return firstName;
    }

    @JsonSetter("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonSetter("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getSex() {
        return sex;
    }

    @JsonSetter("sex")
    public void setSex(int sex) {
        this.sex = sex != 1;
    }

    public String getNickname() {
        return nickname;
    }

    @JsonSetter("nickname")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMaidenName() {
        return maidenName;
    }

    @JsonSetter("maiden_name")
    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public String getScreenName() {
        return screenName;
    }

    @JsonSetter("screen_name")
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getBirthday() {
        return birthday;
    }

    @JsonSetter("bdate")
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    /**
     * Получает текущий список полей для запроса из Вконтакте
     *
     * @return Список полей через запятую
     */
    public String getFieldsToParse() {
        return fieldsToParse;
    }

    /**
     * Устанавливает список полей для запроса из Вконтакте
     *
     * @param fieldsToParse Список полей через запятую
     */
    public void setFieldsToParse(String fieldsToParse) {
        this.fieldsToParse = fieldsToParse;
    }

    /**
     * Парсит данные пользователя Вконтакте
     *
     * @param vkId Уникальный идентификатор пользователя <b>id</b>
     * @return Объект пользователя
     */
    public VkUser parse(int vkId) throws IOException {
        if (correctVkIdFormat(vkId)) {
            ObjectMapper mapper = new ObjectMapper();
            VkUser vkUser;
            String documentToParse = "https://api.vk.com/method/users.get?v=5.24&lang=ru&user_ids="
                    + vkId + "&fields=" + fieldsToParse;
            JsonNode usersGetResult = getJsonNodeFromApi(documentToParse).get("response").get(0);
            vkUser = mapper.readValue(usersGetResult.toString(), VkUser.class);
            return vkUser;
        } else {
            throw new IllegalArgumentException("id пользователя имеет недопустимый формат");
        }
    }

    /**
     * Перегрузка метода parse(int vkId)
     *
     * @see #parse(int)
     * @param screenName Короткое имя пользователя
     * @return Объект пользователя
     * @throws IOException
     */
    public VkUser parse(String screenName) throws IOException {
        int vkId = getUserId(screenName);
        return parse(vkId);
    }

    /**
     * Проверяет id пользователя Вконтакте на соответсвие формату
     *
     * @param vkId Уникальный идентификатор пользователя <b>id</b>
     * @return true, если формат id правильный. false - если неправильный
     */
    private boolean correctVkIdFormat(int vkId) {
        return vkId > VK_MIN_ID && vkId < VK_MAX_ID;
    }

    /**
     * Получает id пользователя Вконтакте по короткому имени
     *
     * @param screenName Короткое имя пользователя
     * @return Уникальный идентификатор пользователя <b>id</b>
     * @throws IOException
     */
    private int getUserId(String screenName) throws IOException {
        JsonNode resolveScreenNameResult = getJsonNodeFromApi("https://api.vk.com/method/utils.resolveScreenName?screen_name="
                + screenName).get("response");
        if (resolveScreenNameResult.hasNonNull("object_id")) {
            return resolveScreenNameResult.get("object_id").asInt();
        } else {
            throw new IllegalArgumentException("Пользователя с таким коротким именем не существует");
        }
    }
}
