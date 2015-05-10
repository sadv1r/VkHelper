package ru.sadv1r.openfms;

import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManagerFactory;

/**
 * Created on 5/10/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public class HibernateUtil {
    private static EntityManagerFactory entityManagerFactory;

    public static void buildEntityManagerFactory(String persistenceUnit) {
        try {
            HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
            entityManagerFactory = hibernatePersistenceProvider.createEntityManagerFactory(persistenceUnit, null);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }
}
