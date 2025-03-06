package ru.boshchenko;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
    }

    public static <T> T[] filter(T[] array, Filter filter) {
        T[] filteredArray = Arrays.copyOf(array, array.length);
        for (int i = 0; i < array.length; i++)
            filteredArray[i] = (T) filter.apply(array[i]);
        return filteredArray;
    }
}
