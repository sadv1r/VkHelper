package ru.sadv1r.openfms;

import org.apache.commons.cli.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
                /* Если аргументов нет */
        if (args.length == 0) {
            System.out.println("Необходимо задать аругменты. Для справки воспользуйтесь -h");
        } else {
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
            actionOptionGroup.addOption(new Option("A", "all", false, "сохраняем всех пользователей (необходима база)"));
            actionOptionGroup.addOption(new Option("i", "info", false, "получить информацию о пользователе"));
            actionOptionGroup.addOption(new Option("F", "friends", false, "получить список друзей пользователя"));
            actionOptionGroup.addOption(Option.builder("m")
                    .longOpt("fields")
                    .desc("сравнить списки пользователей")
                    .hasArgs()
                    .argName("domains")
                    .valueSeparator(',')
                    .build());
            options.addOptionGroup(actionOptionGroup);

        /*options.addOption(Option.builder("f")
                .longOpt("fields")
                .desc("получить информацию о пользователе\n"
                        + "в качестве аргумента можно передать необходимые поля.\n"
                        + "Например: -i id,firstName,lastName\n"
                        + "Полный список доступных полей:\n"
                        + "sex,bdate,city,country," +
                        "photo_max_orig,online,online_mobile,has_mobile,contacts,connections,site," +
                        "education,universities,schools,status," +
                        "last_seen,relation,relatives,counters,screen_name,maiden_name,occupation,activities,interests,music,movies," +
                        "tv,books,games,about,quotes,personal,nickname")
                .hasArgs()
                .argName("fields")
                .valueSeparator(',')
                .build());*/

            options.addOption(Option.builder("f")
                    .longOpt("fields")
                    .desc("установить кастомные поля\n"
                            + "Например: -а sex,bdate,city\n"
                            + "Полный список доступных полей:\n"
                            + "sex,bdate,city,country,"
                            + "photo_max_orig,online,online_mobile,has_mobile,contacts,connections,site,"
                            + "education,universities,schools,status,"
                            + "last_seen,relation,relatives,counters,screen_name,maiden_name,occupation,activities,interests,music,movies,"
                            + "tv,books,games,about,quotes,personal,nickname")
                    .hasArg()
                    .argName("fields")
                    .build());

            CommandLineParser commandLineParser = new DefaultParser();
            CommandLine commandLine = commandLineParser.parse(options, args);

            /* help */
            if (commandLine.hasOption("h")) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp(COMMAND_LINE_SYNTAX, options, true);
            }

            /* vk */
            else if (commandLine.hasOption("vk")) {
                if (commandLine.hasOption("f")) {
                    //String fields[] = commandLine.getOptionValues("f");
                    String fields = commandLine.getOptionValue("f");
                    VkParser.setFieldsToParse(fields);

                /* Вызываем нужный метод через рефлешен
                Class clazz = vkUser.getClass();
                System.out.println(clazz.getDeclaredMethod("get" + args[4]).invoke(vkUser));*/
                }

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
                    if (commandLine.hasOption("u")) {
                        String targetDomain = commandLine.getOptionValue("u");
                        int targetId;
                        if (targetDomain.matches("\\d+")) {
                            targetId = Integer.parseInt(targetDomain);
                        } else {
                            targetId = VkParser.getUserId(targetDomain);
                        }
                        VkUser vkUser = VkParser.parse(targetId);
                        printUserInfo(vkUser);
                    } else {
                        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String inputStr;
                        while ((inputStr = bufferedReader.readLine()) != null) {
                            String targetDomain = inputStr;
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

                } else if (commandLine.hasOption("F")) {
                    String targetDomain = commandLine.getOptionValue("u");
                    int targetId;
                    if (targetDomain.matches("\\d+")) {
                        targetId = Integer.parseInt(targetDomain);
                    } else {
                        targetId = VkParser.getUserId(targetDomain);
                    }

                    ArrayList<Integer> friends = VkParser.parseFriends(targetId);
                    for (int friend : friends) {
                        System.out.println(friend);
                    }
                } else if (commandLine.hasOption("all")) {
                    HibernateUtil.buildEntityManagerFactory("OpenfmsPersistenceUnit");
                    int a = 1;
                    int[] ids = new int[500];

                    while (true) {
                        for (int i = 0; i < 500; i++) {
                            ids[i] = a + i;
                            System.out.print(a + i + "\r");
                        }
                        System.out.println();

                        ArrayList<VkUser> vkUsers = VkParser.parse(ids);
                        for (VkUser vkUser : vkUsers) {
                            try { //FIXME! сделать уже что-то с этим костылем
                                EntityManager entityManager = HibernateUtil.getEntityManager();
                                EntityTransaction entityTransaction = entityManager.getTransaction();
                                entityTransaction.begin();
                                entityManager.merge(vkUser);

                                entityTransaction.commit();
                                entityManager.close();
                            } catch (Exception e) {
                                //System.out.println(e.getMessage());
                            }
                        }
                        a += 500;
                    }
                } else if (commandLine.hasOption("gu")) {
                    String targetDomain = commandLine.getOptionValue("g");
                    int targetId;
                    if (targetDomain.matches("\\d+")) {
                        targetId = Integer.parseInt(targetDomain);
                    } else {
                        targetId = VkParser.getUserId(targetDomain);
                    }
                    System.out.println(VkGroupParser.parseUsers(targetId)); //FIXME! построчный вывод каждого id в parseUsers (не всем скопом)
                } else if (commandLine.hasOption("m")) {
                    String domains[] = commandLine.getOptionValues("m");
                    ArrayList<ArrayList<Integer>> usersMap = new ArrayList<>();
                    for (String domain : domains) {
                        ArrayList<Integer> users = new ArrayList<>();
                        int id = 0;
                        if (domain.matches("\\d+")) {
                            id = Integer.parseInt(domain);
                            users = VkParser.parseFriends(id);
                        } else if (domain.matches("-\\d+")) {
                            id = Integer.parseInt(domain.substring(1));
                            users = VkGroupParser.parseUsers(id);
                        } else {
                            //ввели screenname по этому нужно проверить, группа это или нет
                            //дальше if группа одно, не группа другое
                            System.out.println("пиши id блиадь. ScreenName пока не работают");
                        }
                        usersMap.add(users);
                    }

                    int usersMapCount = usersMap.size();
                    HashMap<Integer, Integer> resultMap = new HashMap<>();

                    for (ArrayList<Integer> integers : usersMap) {
                        for (Integer integer : integers) {
                            if (resultMap.containsKey(integer)) {
                                int temp = resultMap.get(integer);
                                resultMap.remove(integer);
                                resultMap.put(integer, temp + 1);
                            } else {
                                resultMap.put(integer, 1);
                            }
                        }
                    }

                    for (Map.Entry<Integer, Integer> users : resultMap.entrySet()) {
                        //System.out.println(users.getKey() + ":" + users.getValue());
                        if (users.getValue() == usersMapCount) {
                            System.out.println(users.getKey());
                        }
                    }

                } else {
                    System.out.println("Неверные аругменты. Для справки воспользуйтесь -h");
                }
            }
            /* Не -vk и не -h */
            else {
                System.out.println("Неверные аругменты. Для справки воспользуйтесь -h");
            }
        }

    }

    private static void printUserInfo(VkUser vkUser) {
        System.out.println("Имя: " + vkUser.getFirstName());
        System.out.println("Фамилия: " + vkUser.getLastName());
        if (vkUser.getNickname() != null)
            System.out.println("Отчество (ник): " + vkUser.getNickname());

        if (vkUser.getMaidenName() != null)
            if (!vkUser.getSex()) {
                System.out.println("Девичья фамилия: " + vkUser.getMaidenName());
            }

        System.out.print("Пол: ");
        if (vkUser.getSex()) {
            System.out.println("Мужской");
        } else {
            System.out.println("Женский");
        }

        if (vkUser.getBirthday() != null)
            System.out.println("Дата рождения: " + vkUser.getBirthday());

        if (vkUser.getScreenName() != null)
            System.out.println("Скриннейм: " + vkUser.getScreenName());

        if (vkUser.getSite() != null)
            System.out.println("Сайт: " + vkUser.getSite());

        if (vkUser.getTwitter() != null)
            System.out.println("Твиттер: " + vkUser.getTwitter());

        if (vkUser.getSkype() != null)
            System.out.println("Скайп: " + vkUser.getSkype());

        if (vkUser.getInstagram() != null)
            System.out.println("Инстаграм: " + vkUser.getInstagram());

        if (vkUser.getStatus() != null && !vkUser.getStatus().equals(""))
            System.out.println("Статус: " + vkUser.getStatus());

        if (vkUser.getCountry() != null) {
            System.out.println("Страна: " + vkUser.getCountry().getTitle());
        }

        if (vkUser.getCity() != null) {
            System.out.println("Город: " + vkUser.getCity().getTitle());
        }
    }
}
