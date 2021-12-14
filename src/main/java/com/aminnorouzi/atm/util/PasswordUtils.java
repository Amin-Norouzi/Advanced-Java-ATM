package com.aminnorouzi.atm.util;

import java.util.Base64;

public class PasswordUtils {

    public static String generatePassword(int count, boolean letters, boolean numbers) {
        return TextUtils.random(count, letters, numbers);
    }

    public static String encodePassword(String plainTextPassword, String salt) {
        return Base64.getEncoder().encodeToString((salt + plainTextPassword).getBytes());
    }

    public static String decodePassword(String hashedPassword, String salt) {
        return new String(Base64.getDecoder().decode(hashedPassword)).replace(salt, "");
    }
}