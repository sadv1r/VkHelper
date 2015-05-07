package ru.sadv1r.openfms;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.io.IOException;
import java.util.ArrayList;

public class VkUserTest {
    private static final String[] TEST_USERS_SCREEN_NAMES = {"sadv1r", "durov"};
    private static final int[] TEST_USERS_IDS = {9313032, 1};
    private static Validator validator;

    private VkUser vkUser = new VkUser();

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void vkUserIsValid() throws IOException {
        assertEquals(0, validator.validate(vkUser.parse(TEST_USERS_IDS[0])).size());
    }

    @Test
    public void testParseInt() throws Exception {
        assertEquals(TEST_USERS_IDS[0], vkUser.parse(TEST_USERS_SCREEN_NAMES[0]).getVkId());
    }

    @Test
    public void testParseString() throws Exception {
        assertEquals(TEST_USERS_IDS[0], vkUser.parse(TEST_USERS_IDS[0]).getVkId());
    }

    @Test
    public void testParseManyInt() throws Exception {
        ArrayList<VkUser> vkUsers = vkUser.parse(TEST_USERS_IDS);
        for (int i = 0; i < TEST_USERS_IDS.length; i++) {
            assertEquals(TEST_USERS_IDS[i], vkUsers.get(i).getVkId());
        }
    }

    @Test
    public void testParseManyString() throws Exception {
        ArrayList<VkUser> vkUsers = vkUser.parse(TEST_USERS_SCREEN_NAMES);
        for (int i = 0; i < TEST_USERS_IDS.length; i++) {
            assertEquals(TEST_USERS_IDS[i], vkUsers.get(i).getVkId());
        }
    }
}