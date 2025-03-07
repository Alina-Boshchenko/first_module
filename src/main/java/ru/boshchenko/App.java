package ru.boshchenko;

import java.util.HashMap;
import java.util.Map;

public class App {
 
    public static <T> Map<T, Integer> countOfElements(T[] elements) {
        Map<T, Integer> countMap = new HashMap<>();
        for (int i = 0; i < elements.length; i++)
            countMap.merge(elements[i], 1, (oldVal, newVal) -> oldVal + newVal);
        return countMap;
    }
}
