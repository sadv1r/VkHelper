package ru.sadv1r.openfms;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created on 5/10/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public class HybernateUtilTest {
    @Before
    public void setUp() throws Exception {
        HibernateUtil.buildEntityManagerFactory("OpenfmsPersistenceUnit");
    }

    @Test
    public void getEntityManagerFactory() {
        assertTrue(HibernateUtil.getEntityManagerFactory().isOpen());
    }

    @After
    public void tearDown() throws Exception {
        HibernateUtil.closeEntityManagerFactory();
    }
}
