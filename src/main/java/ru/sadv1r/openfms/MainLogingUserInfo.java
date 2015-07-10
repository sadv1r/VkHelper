package ru.sadv1r.openfms;

import ru.sadv1r.openfms.HibernateUtil;
import ru.sadv1r.openfms.VkParser;
import ru.sadv1r.openfms.VkUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;

/**
 * Маленький пример того, как может быть реализовано ведение истории изменений данных
 * Created on 5/10/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public class MainLogingUserInfo {

    public static void main(String[] args) throws Exception {
        final int[] users = {327286, 494446, 699578, 844151};
        HibernateUtil.buildEntityManagerFactory("OpenfmsPersistenceUnit");

        int i = 1;
        while (true) {

            System.out.println("Start " + i++);
            ArrayList<VkUser> vkUsers = VkParser.parse(users);
            for (VkUser vkUser : vkUsers) {
                EntityManager entityManager = HibernateUtil.getEntityManager();
                EntityTransaction entityTransaction = entityManager.getTransaction();
                entityTransaction.begin();

                entityManager.merge(vkUser);

                entityTransaction.commit();
                entityManager.close();
            }
            System.out.println("Done");
            Thread.sleep(300_000); //5 минут
        }

        //HibernateUtil.closeEntityManagerFactory();
    }

}
