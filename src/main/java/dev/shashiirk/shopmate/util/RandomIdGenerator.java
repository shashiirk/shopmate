package dev.shashiirk.shopmate.util;

import java.security.SecureRandom;

public final class RandomIdGenerator {

    private RandomIdGenerator() {
    }

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomId(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHANUMERIC.length()); // Get random index
            sb.append(ALPHANUMERIC.charAt(index)); // Append random character
        }
        return sb.toString(); // Return the generated string
    }

}
