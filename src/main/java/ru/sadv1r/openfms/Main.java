package ru.sadv1r.openfms;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Внимание! Класс содержит адову кучу говнокода!
 * Created on 6/24/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public class Main {
    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if ("-vk".equals(args[0])) {
            if ("-hidden".equals(args[1]) && args.length == 3) {
                if (args[2].matches("\\d+")) {
                    System.out.println("Ищем скрытых друзей:");
                    ArrayList<Integer> hiddenFriends = new ArrayList<>();

                    int targetId = Integer.parseInt(args[2]);
                    System.out.println("Достаем друзей");
                    ArrayList<Integer> targetFriends = VkParser.parseFriends(targetId); //друзья
                    System.out.println("Достал друзей");
                    int a = 0, b;


                    for (int targetFriend : targetFriends) {
                        ArrayList<Integer> friendsOfTargetFriend = VkParser.parseFriends(targetFriend); //друзья друзей
                        b = 0;
                        for (int friendOfTargetFriend : friendsOfTargetFriend) {
                            ArrayList<Integer> friendsOfFriendsOfTargetFriend = VkParser.parseFriends(friendOfTargetFriend); //друзья друзей друзей
                            for (int friendOfFriendOfTargetFriend : friendsOfFriendsOfTargetFriend) {
                                if (targetId == friendOfFriendOfTargetFriend) {
                                    hiddenFriends.add(friendOfTargetFriend);
                                }
                                System.out.println(b * 100 / friendsOfTargetFriend.size() + "% друзей друзей выполнено");
                                System.out.println(a * 100 / targetFriends.size() + "% всего выполнено");
                            }
                            b++;
                        }
                        a++;
                    }
                    for (int i : hiddenFriends) {
                        System.out.println(i);
                    }

                }
            }
        }

        /*Если аргументы есть*/
        if (args.length > 0) {
            /*Работаем с Вконтакте*/
            if (args[0].equals("-vk")) {//{args.length >= 2)
                if (args.length == 2) {

                } else if (args.length == 3) {
                    /*Адрес цели*/
                    if (args[1].equals("-domain")) {
                        VkUser vkUser;
                        /*id*/
                        if (args[2].matches("\\d+")) { //FIXME!!! Делать запрос через vk.utils.resolveScreenName и понимать, что является целью
                            vkUser = VkParser.parse(Integer.parseInt(args[1]));
                            printUserInfo(vkUser);
                        /*Группа*/
                        } else if (args[2].matches("-\\d+")) {
                            System.out.println(VkGroupParser.parseUsers(Integer.parseInt(args[1].replace("-", ""))));
                        /*Полный парсинг пользователей ВК*/
                        } else if (args[2].equals("-all")) {
                            HibernateUtil.buildEntityManagerFactory("OpenfmsPersistenceUnit");
                            int a = 1;
                            int[] ids = new int[500];

                            while (true) {
                                for (int i = 0; i < 500; i++) {
                                    ids[i] = a + i;
                                    System.out.println(a + i);
                                }

                                ArrayList<VkUser> vkUsers = VkParser.parse(ids);
                                for (VkUser vkUserg : vkUsers) {
                                    try {
                                        EntityManager entityManager = HibernateUtil.getEntityManager();
                                        EntityTransaction entityTransaction = entityManager.getTransaction();
                                        entityTransaction.begin();

                                        entityManager.merge(vkUserg);

                                        entityTransaction.commit();
                                        entityManager.close();
                                    } catch (Exception e) {

                                    }
                                }
                                a += 500;
                            }
                        }
                    }
                } else if (args.length == 4 && args[2].equals("-p")) {
                    VkParser.setFieldsToParse(args[3]);
                    VkUser vkUser;
                    if (args[1].matches("\\d")) {
                        vkUser = VkParser.parse(Integer.parseInt(args[1]));
                    } else {
                        vkUser = VkParser.parse(args[1]);
                    }

                    Class clazz = vkUser.getClass();

                    System.out.println(clazz.getDeclaredMethod("get" + args[3]).invoke(vkUser));

                } else {
                    System.out.println("Не верная конфигурация");
                }

            /*Если аргументов нет*/
            } else {
                System.out.println("Не верная конфигурация");
            }
        } else {
            System.out.println("Не верная конфигурация");
        }

    }

    private static void printUserInfo(VkUser vkUser) {
        System.out.println("Имя: " + vkUser.getFirstName());
        System.out.println("Фамилия: " + vkUser.getLastName());
        System.out.println("Отчество (ник): " + vkUser.getNickname());

        if (!vkUser.getSex()) {
            System.out.println("Девичья фамилия: " + vkUser.getMaidenName());
        }

        System.out.print("Пол: ");
        if (vkUser.getSex()) {
            System.out.println("Мужской");
        } else {
            System.out.println("Женский");
        }

        System.out.println("Дата рождения: " + vkUser.getBirthday());
        System.out.println("Скриннейм: " + vkUser.getScreenName());
        System.out.println("Сайт: " + vkUser.getSite());
        System.out.println("Твиттер: " + vkUser.getTwitter());
        System.out.println("Скайп: " + vkUser.getSkype());
        System.out.println("Инстаграм: " + vkUser.getInstagram());
        System.out.println("Статус: " + vkUser.getStatus());

        System.out.print("Страна: ");
        if (vkUser.getCountry() != null) {
            System.out.println(vkUser.getCountry().getTitle());
        } else {
            System.out.println();
        }

        System.out.print("Город: ");
        if (vkUser.getCity() != null) {
            System.out.println(vkUser.getCity().getTitle());
        } else {
            System.out.println();
        }
    }
}
