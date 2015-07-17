package ru.sadv1r.openfms;

import org.apache.commons.cli.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Внимание! Класс содержит адову кучу говнокода!
 * Created on 6/24/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public class Main {
    private static final String COMMAND_LINE_SYNTAX = "java -jar openfms-[version].jar";

    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        Options options = new Options();

        OptionGroup mainOptionGroup = new OptionGroup();
        mainOptionGroup.setRequired(true);
        mainOptionGroup.addOption(new Option("h", "help", false, "вывод этого сообщения"));
        mainOptionGroup.addOption(new Option("vk", "vkontakte", false, "работаем с Вконтакте"));
        /*mainOptionGroup.addOption(Option.builder("vk")
                .longOpt("vkontakte")
                .desc("работаем с Вконтакте\n"
                        + "в качестве аргумента можно передавать id пользователя,\n"
                        + "id группы (перед id группы ставим -) или screenName")
                .argName("domain")
                .optionalArg(true)
                .build());*/
        options.addOptionGroup(mainOptionGroup);

        OptionGroup targetTypeOptionGroup = new OptionGroup();
        targetTypeOptionGroup.addOption(Option.builder("u")
                .longOpt("user")
                .desc("id или screenName пользователя")
                .hasArg()
                .argName("domain")
                .build());
        targetTypeOptionGroup.addOption(Option.builder("g")
                .longOpt("group")
                .desc("id или screenName группы")
                .hasArg()
                .argName("domain")
                .build());
        options.addOptionGroup(targetTypeOptionGroup);

        OptionGroup actionOptionGroup = new OptionGroup();
        actionOptionGroup.addOption(new Option("H", "hidden", false, "поиск скрытых друзей"));
        actionOptionGroup.addOption(new Option("gu", "group-users", false, "получить список пользователей группы"));
        actionOptionGroup.addOption(new Option("A", "all", false, "сохраняем всех пользователей (недоступно)"));
        actionOptionGroup.addOption(Option.builder("i")
                .longOpt("info")
                .desc("получить информацию о пользователе\n"
                        + "в качестве аргумента можно передать необходимые поля.\n"
                        + "Например: -i id,firstName,lastName\n"
                        + "Полный список доступных полей:\n"
                        + "sex,bdate,city,country," +
                        "photo_max_orig,online,online_mobile,has_mobile,contacts,connections,site," +
                        "education,universities,schools,status," +
                        "last_seen,relation,relatives,counters,screen_name,maiden_name,occupation,activities,interests,music,movies," +
                        "tv,books,games,about,quotes,personal,nickname")
                .argName("fields")
                .optionalArg(true)
                .build());
        options.addOptionGroup(actionOptionGroup);

        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse(options, args);

        if (commandLine.hasOption("h")) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp(COMMAND_LINE_SYNTAX, options, true);
        }

        /* РАБОТАЕМ С ВК */
        else if (commandLine.hasOption("vk")) {
            if (commandLine.hasOption("H")) {
                String targetDomain = commandLine.getOptionValue("u");
                int targetId;
                //FIXME! повторяющуюся конструкцию if-ов нужно перенести куда-то. Возможно в VkParser
                if (targetDomain.matches("\\d+")) {
                    targetId = Integer.parseInt(targetDomain);
                } else {
                    targetId = VkParser.getUserId(targetDomain);
                }
                HashSet<Integer> hiddenFriends = new HashSet<>();

                ArrayList<Integer> targetFriends = VkParser.parseFriends(targetId); //друзья
                int a = 0, b;
                try {
                    for (int targetFriend : targetFriends) {
                        ArrayList<Integer> friendsOfTargetFriend = VkParser.parseFriends(targetFriend); //друзья друзей
                        b = 0;
                        for (int friendOfTargetFriend : friendsOfTargetFriend) {
                            ArrayList<Integer> friendsOfFriendsOfTargetFriend = VkParser.parseFriends(friendOfTargetFriend); //друзья друзей друзей
                            for (int friendOfFriendOfTargetFriend : friendsOfFriendsOfTargetFriend) {
                                if (targetId == friendOfFriendOfTargetFriend && !targetFriends.contains(friendOfTargetFriend)) {
                                    hiddenFriends.add(friendOfTargetFriend);
                                }
                            }
                            System.out.print(a * 100 / targetFriends.size() + "% выполнено всего. " + b++ * 100 / friendsOfTargetFriend.size() +
                                    "% выполнено на текущей стадии" + "\r");
                        }
                        a++;
                    }
                    System.out.println();
                } catch (Exception e) {
                    System.out.println("Работа не была завершена. Все, что удалось найти:");
                    for (int i : hiddenFriends) {
                        System.out.println(i);
                    }
                    throw e;
                }

                for (int i : hiddenFriends) {
                    System.out.println(i);
                }
            } else if (commandLine.hasOption("i")) {
                String targetDomain = commandLine.getOptionValue("u");
                int targetId;
                if (targetDomain.matches("\\d+")) {
                    targetId = Integer.parseInt(targetDomain);
                } else {
                    targetId = VkParser.getUserId(targetDomain);
                }
                VkUser vkUser = VkParser.parse(targetId);
                printUserInfo(vkUser);
            }
        }

        /*if ("-vk".equals(args[0])) {
            if ("-hidden".equals(args[1]) && args.length == 3) {
                if (args[2].matches("\\d+")) {
                    HashSet<Integer> hiddenFriends = new HashSet<>();

                    int targetId = Integer.parseInt(args[2]);
                    ArrayList<Integer> targetFriends = VkParser.parseFriends(targetId); //друзья
                    int a = 0, b;
                    try {
                        for (int targetFriend : targetFriends) {
                            ArrayList<Integer> friendsOfTargetFriend = VkParser.parseFriends(targetFriend); //друзья друзей
                            b = 0;
                            for (int friendOfTargetFriend : friendsOfTargetFriend) {
                                ArrayList<Integer> friendsOfFriendsOfTargetFriend = VkParser.parseFriends(friendOfTargetFriend); //друзья друзей друзей
                                for (int friendOfFriendOfTargetFriend : friendsOfFriendsOfTargetFriend) {
                                    if (targetId == friendOfFriendOfTargetFriend && !targetFriends.contains(friendOfTargetFriend)) {
                                        hiddenFriends.add(friendOfTargetFriend);
                                    }
                                }
                                System.out.print(a * 100 / targetFriends.size() + "% выполнено всего. " + b++ * 100 / friendsOfTargetFriend.size() +
                                        "% выполнено на текущей стадии" + "\r");
                            }
                            a++;
                        }
                        System.out.println();
                    } catch (Exception e) {
                        System.out.println("Работа не была завершена. Все, что удалось найти:");
                        for (int i : hiddenFriends) {
                            System.out.println(i);
                        }
                        throw e;
                    }

                    for (int i : hiddenFriends) {
                        System.out.println(i);
                    }
                }
            }
        }*/

        /*Если аргументы есть*/
        if (args.length > 0) {
            /*Работаем с Вконтакте*/
            if (args[0].equals("-vk")) {//{args.length >= 2)
                if (args.length == 2) {
                    /*Полный парсинг пользователей ВК*/
                    if (args[1].equals("-all")) {
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
                } else if (args.length == 3) {
                    /*Адрес цели*/
                    if (args[1].equals("-domain")) {
                        VkUser vkUser;
                        /*id*/
                        if (args[2].matches("\\d+")) { //FIXME!!! Делать запрос через vk.utils.resolveScreenName и понимать, что является целью
                            vkUser = VkParser.parse(Integer.parseInt(args[2]));
                            printUserInfo(vkUser);
                        /*Группа*/
                        } else if (args[2].matches("-\\d+")) {
                            System.out.println(VkGroupParser.parseUsers(Integer.parseInt(args[2].replace("-", ""))));
                        }
                    }
                } else if (args.length == 5 && args[3].equals("-p")) {
                    VkParser.setFieldsToParse(args[4]);
                    VkUser vkUser;
                    if (args[2].matches("\\d+")) {
                        vkUser = VkParser.parse(Integer.parseInt(args[2]));
                    } else {
                        vkUser = VkParser.parse(args[2]);
                    }

                    Class clazz = vkUser.getClass();

                    System.out.println(clazz.getDeclaredMethod("get" + args[4]).invoke(vkUser));

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
