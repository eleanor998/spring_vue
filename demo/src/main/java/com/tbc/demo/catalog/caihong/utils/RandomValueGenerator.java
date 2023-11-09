package com.tbc.demo.catalog.caihong.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomValueGenerator {
    private static final Random random = new Random();
    private static final Set<Object> generatedObjects = new HashSet<>();

    public static void generateRandomValues(Object obj) throws IllegalAccessException {
        generatedObjects.clear();
        generateRandomValuesInternal(obj);
    }

    private static void generateRandomValuesInternal(Object obj) throws IllegalAccessException {
        if (generatedObjects.contains(obj)) {
            return;
        }

        generatedObjects.add(obj);

        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            int modifiers = field.getModifiers();

            if (!Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers)) {
                Class<?> fieldType = field.getType();
                Object fieldValue = field.get(obj);

                if (fieldValue == null) {
                    fieldValue = getRandomValueForType(fieldType);
                    field.set(obj, fieldValue);
                }

                generateRandomValuesInternal(fieldValue);
            }
        }
    }

    private static Object getRandomValueForType(Class<?> fieldType) {
        if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
            return random.nextInt();
        } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
            return random.nextDouble();
        } else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
            return random.nextFloat();
        } else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
            return random.nextLong();
        } else if (fieldType.equals(short.class) || fieldType.equals(Short.class)) {
            return (short) random.nextInt(Short.MAX_VALUE + 1);
        } else if (fieldType.equals(byte.class) || fieldType.equals(Byte.class)) {
            return (byte) random.nextInt(Byte.MAX_VALUE + 1);
        } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
            return random.nextBoolean();
        } else if (fieldType.equals(char.class) || fieldType.equals(Character.class)) {
            return (char) (random.nextInt(Character.MAX_VALUE + 1));
        } else if (fieldType.equals(String.class)) {
            return generateRandomString();
        } else if (fieldType.equals(LocalDate.class)) {
            return LocalDate.now().plusDays(random.nextInt(365));
        } else if (fieldType.equals(LocalTime.class)) {
            return LocalTime.of(random.nextInt(24), random.nextInt(60), random.nextInt(60));
        } else if (fieldType.equals(LocalDateTime.class)) {
            return LocalDateTime.of(
                    LocalDate.now().plusDays(random.nextInt(365)),
                    LocalTime.of(random.nextInt(24), random.nextInt(60), random.nextInt(60))
            );
        } else if (fieldType.equals(Date.class)) {
            return new Date();
        } else if (fieldType.equals(BigDecimal.class)) {
            return BigDecimal.valueOf(random.nextDouble() * 1000);
        } else {
            if (!fieldType.isAssignableFrom(sun.util.calendar.BaseCalendar.Date.class)) {
                try {
                    Object nestedObj = fieldType.getDeclaredConstructor().newInstance();
                    generateRandomValuesInternal(nestedObj);
                    return nestedObj;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
    private static String generateRandomString() {
        // Generate a random string of length 10
        int length = 10;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }
}
