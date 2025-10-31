package org.example;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PersonReader {

    private static final String PHONE_PATTERN = "\\s*(\\+7|8)\\(\\d{3}\\)\\d{3}-?\\d{2}-?\\d{2}";
    private static final Pattern PHONE_REGEX = Pattern.compile(PHONE_PATTERN);

    public static List<Person> readPersons(String resourceFileName) {
        List<Person> persons = new ArrayList<>();

        try (InputStream inputStream = PersonReader.class.getClassLoader().getResourceAsStream(resourceFileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new InvalidPersonDataException("Resource file not found: " + resourceFileName);
            }

            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString().trim();
            parseJsonArray(jsonString, persons);

        } catch (IOException e) {
            throw new InvalidPersonDataException("Error reading resource file: " + resourceFileName, e);
        }

        return persons;
    }

    private static void parseJsonArray(String jsonString, List<Person> persons) {
        if (!jsonString.startsWith("[") || !jsonString.endsWith("]")) {
            throw new InvalidPersonDataException("Invalid JSON format: expected array");
        }

        String content = jsonString.substring(1, jsonString.length() - 1).trim();
        if (content.isEmpty()) {
            return;
        }

        // Split by objects more carefully
        List<String> objectStrings = splitJsonObjects(content);

        for (String objStr : objectStrings) {
            Person person = parsePersonObject(objStr.trim());
            persons.add(person);
        }
    }

    private static List<String> splitJsonObjects(String content) {
        List<String> objects = new ArrayList<>();
        int braceCount = 0;
        StringBuilder currentObject = new StringBuilder();

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);

            if (c == '{') {
                braceCount++;
            } else if (c == '}') {
                braceCount--;
            }

            currentObject.append(c);

            if (braceCount == 0 && currentObject.length() > 0) {
                String obj = currentObject.toString().trim();
                if (!obj.isEmpty() && obj.startsWith("{") && obj.endsWith("}")) {
                    objects.add(obj);
                }
                currentObject = new StringBuilder();

                // Skip comma if present
                if (i + 1 < content.length() && content.charAt(i + 1) == ',') {
                    i++;
                }
            }
        }

        return objects;
    }

    private static Person parsePersonObject(String jsonObject) {
        if (!jsonObject.startsWith("{") || !jsonObject.endsWith("}")) {
            throw new InvalidPersonDataException("Invalid JSON object: " + jsonObject);
        }

        String content = jsonObject.substring(1, jsonObject.length() - 1).trim();

        String firstName = extractFieldValue(content, "first-name");
        String surName = extractFieldValue(content, "family-name");
        String phone = extractFieldValue(content, "phone");
        String ageStr = extractFieldValue(content, "age");

        // Validate required fields
        if (firstName == null || firstName.isEmpty()) {
            throw new InvalidPersonDataException("First name is missing or empty");
        }
        if (surName == null || surName.isEmpty()) {
            throw new InvalidPersonDataException("Surname is missing or empty");
        }
        if (phone == null) {
            throw new InvalidPersonDataException("Phone is missing");
        }
        if (ageStr == null || ageStr.isEmpty()) {
            throw new InvalidPersonDataException("Age is missing");
        }

        // Parse age
        byte age;
        try {
            age = Byte.parseByte(ageStr);
        } catch (NumberFormatException e) {
            throw new InvalidPersonDataException("Invalid age format: " + ageStr);
        }

        // Validate and transform phone
        String transformedPhone = validateAndTransformPhone(phone);

        // Validate age
        if (age < 0 || age > 120) {
            throw new InvalidPersonDataException("Age must be between 0 and 120, got: " + age);
        }

        return new Person(firstName, surName, transformedPhone, age);
    }

    private static String extractFieldValue(String jsonContent, String fieldName) {
        String pattern = "\"" + fieldName + "\"\\s*:\\s*\"?([^,\"]+)\"?";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(jsonContent);

        if (matcher.find()) {
            String value = matcher.group(1).trim();
            // Remove quotes if present
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            return value;
        }
        return null;
    }

    private static String validateAndTransformPhone(String phone) {
        if (!PHONE_REGEX.matcher(phone).matches()) {
            throw new InvalidPersonDataException("Invalid phone format: " + phone);
        }

        // Remove all non-digit characters
        String digitsOnly = phone.replaceAll("\\D", "");

        // Extract last 10 digits (remove country code if present)
        if (digitsOnly.length() == 11) {
            return digitsOnly.substring(1); // Remove first digit (country code)
        } else if (digitsOnly.length() == 10) {
            return digitsOnly;
        } else {
            throw new InvalidPersonDataException("Unexpected phone number length after cleaning: " + digitsOnly);
        }
    }
}