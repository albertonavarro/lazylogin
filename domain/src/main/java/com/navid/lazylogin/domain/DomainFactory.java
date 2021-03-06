package com.navid.lazylogin.domain;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author alberto
 */
public class DomainFactory {

    private static final int VERSION = 1;

    private static final Random RANDOM = new Random();

    private static final SecureRandom secureRandom = new SecureRandom();

    public static boolean sessionIdValidator(String sessionId) {
        return true;
    }

    public static String sessionIdGenerator(String email, String token) {
        String variable = UUID.randomUUID().toString();

        int fixed = Math.abs((email + token).hashCode());

        return VERSION + "-" + fixed + "-" + variable;
    }

    public static String validationKeyGenerator(String value) {
        String variable = UUID.randomUUID().toString();

        return VERSION + "-" + variable;
    }

    public static String tokenGenerator(String email) {
        String variable = UUID.randomUUID().toString();

        int fixed = Math.abs(email.hashCode());

        return VERSION + "-" + fixed + "-" + variable + "-" + System.currentTimeMillis();
    }

    public static boolean validationKeyValidator(String validationKey) {
        return true;
    }

}
