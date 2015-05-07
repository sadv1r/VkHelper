package ru.sadv1r.openfms;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MySqlTest {
    private static final String SERVER_NAME = "localhost";
    private static final int PORT_NUMBER = 3306;
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    private static final String DATABASE = "openfms";
    private static MySql mySql;

    @BeforeClass
    public static void setUp() throws Exception {
        mySql = new MySql(SERVER_NAME, PORT_NUMBER, USER_NAME, PASSWORD, DATABASE);
    }

    @Test
    public void testConnection() throws Exception {
        assertTrue(mySql != null);
    }

    @Test
    public void testGetVkIdsToParse() throws Exception {
        assertEquals(1, (int) mySql.getVkIdsToParse(0).get(0));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        mySql.close();
    }
}