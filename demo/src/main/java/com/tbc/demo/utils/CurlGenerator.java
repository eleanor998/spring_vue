package com.tbc.demo.utils;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Random;

public class CurlGenerator {

    private static final String DEFAULT_BASE_URL = "http://localhost:8080";

    private static ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    public static String generateCurlCommand(Method method) {
        if (method == null) {
            return "Method not found!";
        }

        String[] requestPaths = getRequestPaths(method);
        if (requestPaths.length == 0) {
            return "RequestMapping annotation not found!";
        }

        StringBuilder curlCommand = new StringBuilder("curl ");
        RequestMethod[] requestMethods = getRequestMethods(method);

        for (int i = 0; i < requestPaths.length; i++) {
            curlCommand.append("-X ").append(requestMethods[i].name()).append(" ");
            curlCommand.append("'").append(DEFAULT_BASE_URL).append(requestPaths[i]).append("' ");
        }

        Parameter[] parameters = method.getParameters();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequestParam) {
                    RequestParam requestParam = (RequestParam) annotation;
                    String paramName = requestParam.value();
                    String paramValue = getRequestParamValue(parameterNames[i], parameterTypes[i]);

                    curlCommand.append("-d '").append(paramName).append("=").append(paramValue).append("' ");
                } else if (annotation instanceof PathVariable) {
                    PathVariable pathVariable = (PathVariable) annotation;
                    String paramName = pathVariable.value();
                    String paramValue = getPathVariableValue(parameterNames[i], parameterTypes[i]);

                    curlCommand.append("'").append(DEFAULT_BASE_URL).append(requestPaths[0].replace("{" + paramName + "}", paramValue)).append("' ");
                } else if (annotation instanceof RequestBody) {
                    RequestBody requestBody = (RequestBody) annotation;
                    String requestBodyJson = getRequestBodyJson(parameterTypes[i]);

                    curlCommand.append("-H 'Content-Type: application/json' ");
                    curlCommand.append("-d '").append(requestBodyJson).append("' ");
                }
            }
        }

        return curlCommand.toString();
    }


    private static String[] getRequestPaths(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            return requestMapping.value();
        }

        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping != null) {
            return getMapping.value();
        }

        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null) {
            return postMapping.value();
        }

        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        if (putMapping != null) {
            return putMapping.value();
        }

        return new String[0];
    }

    private static RequestMethod[] getRequestMethods(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            return requestMapping.method();
        }

        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping != null) {
            return new RequestMethod[]{RequestMethod.GET};
        }

        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null) {
            return new RequestMethod[]{RequestMethod.POST};
        }

        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        if (putMapping != null) {
            return new RequestMethod[]{RequestMethod.PUT};
        }

        return new RequestMethod[0];
    }

    private static String getRequestParamValue(String parameterName, Class<?> parameterType) {
        String defaultValue = generateRandomDefaultValue(parameterType);
        return defaultValue != null ? defaultValue : "";
    }

    private static String getPathVariableValue(String parameterName, Class<?> parameterType) {
        String defaultValue = generateRandomDefaultValue(parameterType);
        return defaultValue != null ? defaultValue : "";
    }

    private static String getRequestBodyJson(Class<?> parameterType) {
        Object defaultValue = generateDefaultObject(parameterType);
        return JsonUtils.toJson(defaultValue);
    }

    private static String generateRandomDefaultValue(Class<?> type) {
        if (type.equals(String.class)) {
            return getRandomStringValue();
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
            return String.valueOf(new Random().nextInt(100));
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return String.valueOf(new Random().nextDouble());
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            return String.valueOf(new Random().nextFloat());
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return String.valueOf(new Random().nextBoolean());
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return String.valueOf(new Random().nextLong());
        } else if (type.equals(short.class) || type.equals(Short.class)) {
            return String.valueOf((short) new Random().nextInt(Short.MAX_VALUE + 1));
        } else if (type.equals(byte.class) || type.equals(Byte.class)) {
            return String.valueOf((byte) new Random().nextInt(Byte.MAX_VALUE + 1));
        } else if (type.equals(char.class) || type.equals(Character.class)) {
            return "'" + (char) (new Random().nextInt(26) + 'a') + "'";
        } else {
            return null;
        }
    }

    private static Object generateDefaultObject(Class<?> type) {
        if (type.equals(String.class)) {
            return getRandomStringValue();
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
            return new Random().nextInt(100);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return new Random().nextDouble();
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            return new Random().nextFloat();
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return new Random().nextBoolean();
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return new Random().nextLong();
        } else if (type.equals(short.class) || type.equals(Short.class)) {
            return (short) new Random().nextInt(Short.MAX_VALUE + 1);
        } else if (type.equals(byte.class) || type.equals(Byte.class)) {
            return (byte) new Random().nextInt(Byte.MAX_VALUE + 1);
        } else if (type.equals(char.class) || type.equals(Character.class)) {
            return (char) (new Random().nextInt(26) + 'a');
        } else {
            try {
                return type.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static String getRandomStringValue() {
        int length = 10;
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
