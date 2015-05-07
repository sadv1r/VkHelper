package ru.sadv1r.openfms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
    private static final int MAX_USERS_TO_PARSE_AT_ONCE = 500;
    private String fieldsToParse = "sex,bdate,city,country,photo_50,photo_100,photo_200_orig,photo_200,photo_400_orig," +
            "photo_max,photo_max_orig,photo_id,online,online_mobile,domain,has_mobile,contacts,connections,site," +
            "education,universities,schools,can_post,can_see_all_posts,can_see_audio,can_write_private_message,status," +
            "last_seen,relation,relatives,counters,screen_name,maiden_name,occupation,activities,interests,music,movies," +
            "tv,books,games,about,quotes,personal,nickname";

    @JsonProperty("id")
    @NotNull
    @Min(VK_MIN_ID)
    @Max(VK_MAX_ID)
    private int vkId;

    @JsonProperty("first_name")
    @Size(min = 1, max = 32)
    private String firstName;

    @JsonProperty("last_name")
    @Size(min = 1, max = 32)
    private String lastName;

    @JsonProperty("sex")
    private boolean sex;

    @JsonProperty("nickname")
    @Size(min = 1, max = 32)
    private String nickname;

    @JsonProperty("maiden_name")
    @Size(min = 1, max = 32)
    private String maidenName;

    @JsonProperty("screen_name")
    @Size(min = 1, max = 32)
    private String screenName;

    @JsonProperty("bdate")
    @Size(min = 3, max = 10)
    private String birthday;

    @JsonProperty("city")
    private City city;

    public class City {
        @JsonProperty("id")
        @Min(1)
        //FIXME @Max(?)
        private int id;

        @JsonProperty("title")
        @Size(min = 3, max = 32)
        private String title;

        public int getId() {
            return id;
        }

        @JsonSetter("id")
        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        @JsonSetter("title")
        public void setTitle(String title) {
            this.title = title;
        }
    }

    @JsonProperty("country")
    private Country country;

    public class Country {
        @JsonProperty("id")
        @Min(1)
        //FIXME @Max(?)
        private int id;

        @Size(min = 3, max = 32)
        private String title;

        public int getId() {
            return id;
        }

        @JsonSetter("id")
        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        @JsonSetter("title")
        public void setTitle(String title) {
            this.title = title;
        }
    }

    @JsonProperty("schools")
    private School[] schools;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class School {
        @JsonProperty("id")
        private int id;

        @JsonProperty("country")
        private int country;

        @JsonProperty("city")
        private int city;

        @JsonProperty("name")
        private String name;

        @JsonProperty("year_from")
        private int yearFrom;

        @JsonProperty("year_to")
        private int yearTo;

        @JsonProperty("year_graduated")
        private int yearGraduated;

        @JsonProperty("class")
        private String classLetter;

        @JsonProperty("speciality")
        private String speciality;

        @JsonProperty("type")
        private int type;

        @JsonProperty("type_str")
        private String typeStr;

        public int getId() {
            return id;
        }

        @JsonSetter("id")
        public void setId(int id) {
            this.id = id;
        }

        public int getCountry() {
            return country;
        }

        @JsonSetter("country")
        public void setCountry(int country) {
            this.country = country;
        }

        public int getCity() {
            return city;
        }

        @JsonSetter("city")
        public void setCity(int city) {
            this.city = city;
        }

        public String getName() {
            return name;
        }

        @JsonSetter("name")
        public void setName(String name) {
            this.name = name;
        }

        public int getYearFrom() {
            return yearFrom;
        }

        @JsonSetter("year_from")
        public void setYearFrom(int yearFrom) {
            this.yearFrom = yearFrom;
        }

        public int getYearTo() {
            return yearTo;
        }

        @JsonSetter("year_to")
        public void setYearTo(int yearTo) {
            this.yearTo = yearTo;
        }

        public int getYearGraduated() {
            return yearGraduated;
        }

        @JsonSetter("year_graduated")
        public void setYearGraduated(int yearGraduated) {
            this.yearGraduated = yearGraduated;
        }

        public String getClassLetter() {
            return classLetter;
        }

        @JsonSetter("class")
        public void setClassLetter(String classLetter) {
            this.classLetter = classLetter;
        }

        public String getSpeciality() {
            return speciality;
        }

        @JsonSetter("speciality")
        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        public int getType() {
            return type;
        }

        @JsonSetter("type")
        public void setType(int type) {
            this.type = type;
        }

        public String getTypeStr() {
            return typeStr;
        }

        @JsonSetter("type_str")
        public void setTypeStr(String typeStr) {
            this.typeStr = typeStr;
        }
    }


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

    public City getCity() {
        return city;
    }

    @JsonSetter("city")
    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    @JsonSetter("country")
    public void setCountry(Country country) {
        this.country = country;
    }

    public School[] getSchools() {
        return schools;
    }

    @JsonSetter("schools")
    public void setSchools(School[] schools) {
        this.schools = schools;
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
            String documentToParse = "https://api.vk.com/method/users.get?v=5.24&lang=ru&user_ids="
                    + vkId + "&fields=" + fieldsToParse;
            JsonNode usersGetResult = getJsonNodeFromApi(documentToParse).get("response").get(0);
            VkUser vkUser = mapper.readValue(usersGetResult.toString(), VkUser.class);
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
     * Парсит данные пользователей Вконтакте
     *
     * @param ids Уникальные идентификаторы пользователей <b>id</b>. Не более 500
     * @return Объекты пользователей
     * @throws IOException
     */
    public ArrayList<VkUser> parse(int[] ids) throws IOException {
        int idsLength = ids.length;
        if (idsLength <= MAX_USERS_TO_PARSE_AT_ONCE) {
            for (int id : ids) {
                if (!correctVkIdFormat(id)) {
                    throw new IllegalArgumentException("id пользователя имеет недопустимый формат: " + id);
                }
            }
            ArrayList<VkUser> vkUsers = new ArrayList<>(idsLength);
            String stringIds = Arrays.toString(ids).replaceAll("[\\[ \\]]", "");
            ObjectMapper mapper = new ObjectMapper();
            String documentToParse = "https://api.vk.com/method/users.get?v=5.24&lang=ru&user_ids="
                    + stringIds + "&fields=" + fieldsToParse;
            JsonNode usersGetResult = getJsonNodeFromApi(documentToParse).get("response");
            for (int i = 0; i < idsLength; i++) {
                VkUser vkUser = mapper.readValue(usersGetResult.get(i).toString(), VkUser.class);
                vkUsers.add(vkUser);
            }
            return vkUsers;
        } else {
            throw new IllegalArgumentException("Количество идентификаторов не может быть больше " + MAX_USERS_TO_PARSE_AT_ONCE);
        }
    }

    /**
     * Перегрузка метода parse(int[] ids)
     *
     * @see #parse(int[])
     * @param screenNames Короткие имена пользователей
     * @return Объекты пользователей
     * @throws IOException
     */
    public ArrayList<VkUser> parse(String[] screenNames) throws IOException {
        int screenNamesLength = screenNames.length;
        int[] ids = new int[screenNamesLength];
        for (int i = 0; i < screenNamesLength; i++) {
            ids[i] = getUserId(screenNames[i]);
        }
        return parse(ids);
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
