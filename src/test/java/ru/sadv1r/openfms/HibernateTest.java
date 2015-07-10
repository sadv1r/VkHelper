package ru.sadv1r.openfms;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created on 5/10/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public class HibernateTest {
    //@Ignore
    @Before
    public void setUp() throws Exception {
        HibernateUtil.buildEntityManagerFactory("OpenfmsPersistenceUnitTest");
    }

    //@Ignore
    @Test
    public void getEntityManagerFactory() throws Exception {
        assertTrue(HibernateUtil.getEntityManagerFactory().isOpen());
    }

    /*@Test
    public void updateDbEntity() throws Exception {
        VkUser vkUser = new VkUser();
        vkUser.setPhotoId("0_123");
        EntityManager entityManager = HibernateUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(vkUser);
        entityTransaction.commit();
        entityManager.refresh(vkUser);
        assertEquals(123, vkUser.getPhotoId());

        vkUser.setPhotoId("0_456");
        entityManager.merge(vkUser);
        entityManager.refresh(vkUser);
        assertEquals(456, vkUser.getVkId());
        entityManager.close();
    }*/

    //@Ignore
    @After
    public void tearDown() throws Exception {
        HibernateUtil.closeEntityManagerFactory();
    }
}
