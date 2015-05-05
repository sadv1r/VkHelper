import org.junit.Test;

import static org.junit.Assert.*;

public class VkUserTest {
    private static final String TEST_USER_SCREEN_NAME = "sadv1r";
    private static final int TEST_USER_ID = 9313032;

    VkUser vkUser = new VkUser();

    @Test
    public void testParseInt() throws Exception {
        assertEquals(TEST_USER_ID, vkUser.parse(TEST_USER_SCREEN_NAME).getVkId());
    }

    @Test
    public void testParseString() throws Exception {
        assertEquals(TEST_USER_ID, vkUser.parse(TEST_USER_ID).getVkId());
    }
}