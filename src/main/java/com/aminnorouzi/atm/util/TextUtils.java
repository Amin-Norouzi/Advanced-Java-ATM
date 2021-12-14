package com.aminnorouzi.atm.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

public class TextUtils {

    public static String capitalize(String text) {
        int count = 0;
        StringBuilder capitalizedText = new StringBuilder();
        String[] splitText = text.split("\\s");
        for (String name : splitText) {
            count++;
            if (count == splitText.length) {
                capitalizedText.append(org.apache.commons.lang.StringUtils.capitalize(name));
                continue;
            }
            capitalizedText.append(org.apache.commons.lang.StringUtils.capitalize(name)).append(" ");
        }
        return capitalizedText.toString();
    }

    public static String toCurrency(String amount) {
        boolean isNegative = false;

        if (amount.contains("-")) {
            amount = amount.replace("-", "");
            isNegative = true;
        }

        String[] textToArray = amount.split("");
        StringBuilder customizedText = new StringBuilder(amount);

        int count = textToArray.length;
        int three = 0;

        while (count-- != 0) {
            three++;
            if (three == 3 && count != 0) {
                customizedText.insert(count, ",");
                three = 0;
            }
        }

        if (isNegative) {
            customizedText.insert(0, "-");
        }

        return customizedText.toString();
    }

    public static String hide(int start, int end, String mark, String text) {
        return new StringBuffer(text).replace(start, end, mark).toString();
    }

    public static String random(int count, boolean letters, boolean numbers) {
        return RandomStringUtils.random(count, letters, numbers);
    }

    public static String normalize(String text) {
        return StringUtils.normalizeSpace(text);
    }

    public static boolean isBlack(String text) {
        return StringUtils.isBlank(text);
    }
}
