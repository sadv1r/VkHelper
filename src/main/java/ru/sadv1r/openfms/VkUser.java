package ru.sadv1r.openfms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Пользователь социальной сети Вконтакте
 * Created on 5/4/15.
 *
 * @author sadv1r
 * @version 1.0
 */
@Entity
@Audited
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkUser extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty("id")
    private int vkId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("sex")
    private boolean sex;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("maiden_name")
    private String maidenName;

    @JsonProperty("screen_name")
    private String screenName;

    @JsonProperty("bdate")
    private String birthday;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty("city")
    private City city;

    @Entity
    @Audited
    public static class City implements Serializable {
        private static final long serialVersionUID = 1L;
        private static final int VK_CITY_MIN_ID = 1;
        private static final int VK_CITY_MAX_ID = 1_000_000_000;
        @Id
        @JsonProperty("id")
        private int cityId;

        @JsonProperty("title")
        private String title;

        public int getCityId() {
            return cityId;
        }

        @JsonSetter("id")
        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getTitle() {
            return title;
        }

        @JsonSetter("title")
        public void setTitle(String title) {
            this.title = title;
        }
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty("country")
    private Country country;

    @Entity
    @Audited
    public static class Country implements Serializable {
        private static final long serialVersionUID = 1L;
        private static final int VK_COUNTRY_MIN_ID = 1;
        private static final int VK_COUNTRY_MAX_ID = 300;
        @Id
        @JsonProperty("id")
        private int countryId;

        @JsonProperty("title")
        private String title;

        public int getCountryId() {
            return countryId;
        }

        @JsonSetter("id")
        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }

        public String getTitle() {
            return title;
        }

        @JsonSetter("title")
        public void setTitle(String title) {
            this.title = title;
        }
    }

    @Transient
    @ManyToOne
    @JsonProperty("schools")
    private School[] schools;

    @Entity
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class School implements Serializable {
        private static final long serialVersionUID = 1L;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonProperty("id")
        private int id;

        @JsonProperty("id")
        private int schoolId;

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

        public int getSchoolId() {
            return schoolId;
        }

        @JsonSetter("id")
        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
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

    @Transient
    @ManyToOne
    @JsonProperty("universities")
    private University[] universities;

    @Entity
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class University implements Serializable {
        private static final long serialVersionUID = 1L;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @JsonProperty("id")
        private int universityId;

        @JsonProperty("country")
        private int country;

        @JsonProperty("city")
        private int city;

        @JsonProperty("name")
        private String name;

        @JsonProperty("faculty")
        private int faculty;

        @JsonProperty("faculty_name")
        private String facultyName;

        @JsonProperty("chair")
        private int chair;

        @JsonProperty("chair_name")
        private String chairName;

        @JsonProperty("graduation")
        private int graduation;

        public int getUniversityId() {
            return universityId;
        }

        @JsonSetter("id")
        public void setUniversityId(int universityId) {
            this.universityId = universityId;
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

        public int getFaculty() {
            return faculty;
        }

        @JsonSetter("faculty")
        public void setFaculty(int faculty) {
            this.faculty = faculty;
        }

        public String getFacultyName() {
            return facultyName;
        }

        @JsonSetter("faculty_name")
        public void setFacultyName(String facultyName) {
            this.facultyName = facultyName;
        }

        public int getChair() {
            return chair;
        }

        @JsonSetter("chair")
        public void setChair(int chair) {
            this.chair = chair;
        }

        public String getChairName() {
            return chairName;
        }

        @JsonSetter("chair_name")
        public void setChairName(String chairName) {
            this.chairName = chairName;
        }

        public int getGraduation() {
            return graduation;
        }

        @JsonSetter("graduation")
        public void setGraduation(int graduation) {
            this.graduation = graduation;
        }
    }

    @JsonProperty("photo_max_orig")
    private String profilePhotoUrl;

    @JsonProperty("site")
    private String site;

    @JsonProperty("twitter")
    private String twitter;

    @JsonProperty("instagram")
    private String instagram;

    @JsonProperty("status")
    private String status;

    @JsonProperty("skype")
    private String skype;


    @ElementCollection
    private Set<Integer> friends;


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
        this.nickname = nickname.replaceAll("\\p{S}", "");
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

    public University[] getUniversities() {
        return universities;
    }

    @JsonSetter("universities")
    public void setUniversities(University[] universities) {
        this.universities = universities;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    @JsonSetter("photo_max_orig")
    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getSite() {
        return site;
    }

    @JsonSetter("site")
    public void setSite(String site) {
        int vkId = this.getVkId();
        if (site.equals("") && vkId == 7364710)
            this.site = "http://ктогей.рф";
        else
            this.site = site;
    }

    public String getTwitter() {
        return twitter;
    }

    @JsonSetter("twitter")
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    @JsonSetter("instagram")
    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getStatus() {
        return status;
    }

    @JsonSetter("status")
    public void setStatus(String status) {
        this.status = status.replaceAll("\\p{S}", "").replaceAll("\\\\", "");
    }

    public String getSkype() {
        return skype;
    }

    @JsonSetter("skype")
    public void setSkype(String skype) {
        this.skype = skype;
    }

    public Set<Integer> getFriends() {
        return friends;
    }

    public void setFriends(Set<Integer> friends) {
        this.friends = friends;
    }
}
