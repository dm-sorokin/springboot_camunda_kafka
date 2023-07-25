package ru.dmitry.springboot_camunda_kafka.util;

import java.util.Random;

public class RandomStringBuilder {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString() {
        int lengthString = new Random().nextInt(10) + 1;
        StringBuilder randomString = new StringBuilder(lengthString);
        for (int i = 0; i < lengthString; i++) {
            int randomIndex = new Random().nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }
}