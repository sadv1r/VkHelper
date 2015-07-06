package ru.sadv1r.openfms;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created on 5/10/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public class HibernateTest {
    @Before
    public void setUp() throws Exception {
        HibernateUtil.buildEntityManagerFactory("OpenfmsPersistenceUnitTest");
    }

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

    @After
    public void tearDown() throws Exception {
        HibernateUtil.closeEntityManagerFactory();
    }
}
