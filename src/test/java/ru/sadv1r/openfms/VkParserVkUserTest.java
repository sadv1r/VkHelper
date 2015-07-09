package ru.sadv1r.openfms;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VkParserVkUserTest {
    private static final String[] TEST_USERS_SCREEN_NAMES = {"sadv1r", "durov"};
    private static final int[] TEST_USERS_IDS = {9313032, 1};
    private static final int RANDOM_USERS_TO_TEST = 50;
    private static final int TEST_VK_MAX_ID = 300_000_000;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void vkUserIsValid() throws IOException {
        assertEquals(0, validator.validate(VkParser.parse(TEST_USERS_IDS[0])).size());
    }

    @Test
    public void vkUserIsValidMany() throws IOException {
        int vkId;
        for (int i = 0; i < RANDOM_USERS_TO_TEST; i++) {
            vkId = (int) (Math.random() * TEST_VK_MAX_ID) + 1;
            Set<ConstraintViolation<VkUser>> constraintViolations = validator.validate(VkParser.parse(vkId));
            if (!constraintViolations.isEmpty()) {
                for (ConstraintViolation<VkUser> constraintViolation : constraintViolations) {
                    System.out.println(constraintViolation.getMessage());
                }
                assertTrue(false);
            } else {
                assertTrue(true);
            }
        }
    }

    @Test
    public void testParseInt() throws Exception {
        assertEquals(TEST_USERS_IDS[0], VkParser.parse(TEST_USERS_SCREEN_NAMES[0]).getVkId());
    }

    @Test
    public void testParseString() throws Exception {
        assertEquals(TEST_USERS_IDS[0], VkParser.parse(TEST_USERS_IDS[0]).getVkId());
    }

    @Test
    public void testParseManyInt() throws Exception {
        ArrayList<VkUser> vkUsers = VkParser.parse(TEST_USERS_IDS);
        for (int i = 0; i < TEST_USERS_IDS.length; i++) {
            assertEquals(TEST_USERS_IDS[i], vkUsers.get(i).getVkId());
        }
    }

    @Test
    public void testParseManyString() throws Exception {
        ArrayList<VkUser> vkUsers = VkParser.parse(TEST_USERS_SCREEN_NAMES);
        for (int i = 0; i < TEST_USERS_IDS.length; i++) {
            assertEquals(TEST_USERS_IDS[i], vkUsers.get(i).getVkId());
        }
    }

    @Test
    public void vkUserIsBetepok() throws IOException {
        assertEquals("http://ктогей.рф", VkParser.parse(7364710).getSite());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        validatorFactory.close();
    }
}