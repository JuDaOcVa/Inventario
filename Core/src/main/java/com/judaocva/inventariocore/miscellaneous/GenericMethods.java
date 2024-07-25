package com.judaocva.inventariocore.miscellaneous;

import java.text.MessageFormat;
import java.util.Random;

public class GenericMethods {

    public static String generateRandomChars() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 4;
        return new Random().ints(length, 0, allowedChars.length())
                .mapToObj(allowedChars::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static String generateToken() {
        return MessageFormat.format("TOKEN-{0}-{1}", generateRandomChars(), generateRandomChars());
    }
}
