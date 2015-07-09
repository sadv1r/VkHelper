package ru.sadv1r.openfms;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created on 6/24/15.
 *
 * @author sadv1r
 * @version 0.1
 */
public class Main {
    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (args.length > 0) {
            if (args[0].equals("-vk") && args.length >= 2) {
                if (args.length == 2) {
                    VkUser vkUser;
                    if (args[1].matches("\\d")) {
                        vkUser = VkParser.parse(Integer.parseInt(args[1]));
                    } else {
                        vkUser = VkParser.parse(args[1]);
                    }


                    System.out.println("Имя: " + vkUser.getFirstName());
                    System.out.println("Фамилия: " + vkUser.getLastName());
                    System.out.println("Отчество (ник): " + vkUser.getNickname());

                    if (!vkUser.getSex()) {
                        System.out.println("Девичья фамилия: " + vkUser.getMaidenName());
                    }

                    System.out.print("Пол: ");
                    if (vkUser.getSex())
                        System.out.println("Мужской");
                    else
                        System.out.println("Женский");

                    System.out.println("Дата рождения: " + vkUser.getBirthday());
                    System.out.println("Скриннейм: " + vkUser.getScreenName());
                    System.out.println("Сайт: " + vkUser.getSite());
                    System.out.println("Твиттер: " + vkUser.getTwitter());
                    System.out.println("Скайп: " + vkUser.getSkype());
                    System.out.println("Инстаграм: " + vkUser.getInstagram());
                    System.out.println("Статус: " + vkUser.getStatus());
                    System.out.println("Страна: " + vkUser.getCountry().getTitle());
                    System.out.println("Город: " + vkUser.getCity().getTitle());
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

                }
            }




        } else {
            System.out.println("Не верная конфигурация");
        }





    }
}
