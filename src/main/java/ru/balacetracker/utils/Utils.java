package ru.balacetracker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.HashSet;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class Utils {
    public static String cutStringForLog(String str, int size) {
        if (str == null) {
            return null;
        }
        String strCompressed = str.trim().replaceAll("\\s+", " ");
        if (strCompressed.length() > size) {
            return strCompressed.substring(0, size) + "...";
        }
        return strCompressed;
    }

    public static String toStringOfNullable(Object obj) {
        return obj == null ? null : obj.toString();
    }

    public static <T> T jsonToObjectLoosely(String body, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(body, clazz);
    }

    public static <T> HashSet<T> nullableCollectionToHashSet(Collection<T> iterable) {
        return iterable == null ? new HashSet<>() : new HashSet<T>(iterable);
    }
}
