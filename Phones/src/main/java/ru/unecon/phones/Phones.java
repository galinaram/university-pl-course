package ru.unecon.phones;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Phones {

    public static List<String> phoneNumbers(List<String> list) {
        List<String> result = new ArrayList<>();

        if (list == null) {
            return result;
        }

        for (String phone : list) {
            if (phone == null || phone.isEmpty()) {
                continue;
            }
            if (phone.contains(" ")) {
                continue;
            }

            String normalized = normalizePhone(phone);
            if (normalized != null) {
                result.add(normalized);
            }
        }

        return result;
    }

    private static String normalizePhone(String phone) {
        String tenDigit = checkTenDigit(phone);
        if (tenDigit != null) {
            return tenDigit;
        }

        String sevenDigit = checkSevenDigit(phone);
        if (sevenDigit != null) {
            return sevenDigit;
        }

        return null;
    }

    private static String checkTenDigit(String phone) {

        Pattern pattern = Pattern.compile("^(\\+7|8)\\((\\d{3})\\)(\\d{7}|\\d{3}-\\d{4}|\\d{3}-\\d{2}-\\d{2})$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            String code = matcher.group(2);
            String numberPart = matcher.group(3).replace("-", "");

            if (numberPart.length() == 7) {
                return code + numberPart;
            }
        }

        return null;
    }

    private static String checkSevenDigit(String phone) {
        if (!phone.matches("^\\d{7}$|^\\d{3}-\\d{4}$|^\\d{3}-\\d{2}-\\d{2}$")) {
            return null;
        }

        String cleanNumber = phone.replace("-", "");

        if (cleanNumber.length() != 7) {
            return null;
        }

        if (phone.matches("^\\d{7}$")) {
            return cleanNumber;
        } else if (phone.matches("^\\d{3}-\\d{4}$")) {
            return cleanNumber;
        } else if (phone.matches("^\\d{3}-\\d{2}-\\d{2}$")) {
            return cleanNumber;
        }

        return null;
    }
}