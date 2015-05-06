package ru.sadv1r.openfms;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class VkUserTest {
    private static final String[] TEST_USERS_SCREEN_NAMES = {"sadv1r", "durov"};
    private static final int[] TEST_USERS_IDS = {9313032, 1};

    VkUser vkUser = new VkUser();

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
}