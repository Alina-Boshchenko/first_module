package ru.boshchenko;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
    }

    public static <T> Map<T, Integer> countOfElements(T[] elements) {
        Map<T, Integer> countMap = new HashMap<>();
        for (int i = 0; i < elements.length; i++) {
            countMap.put(elements[i], countMap.getOrDefault(elements[i], 0) + 1);
        }
        return countMap;
    }
}
