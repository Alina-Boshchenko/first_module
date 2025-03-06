package ru.boshchenko;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    void testStringFilter() {
        String[] input = {"hello", "world"};
        Filter<String> toUpperCase = s -> s.toUpperCase();

        String[] result = App.filter(input, toUpperCase);

        assertArrayEquals(new String[]{"HELLO", "WORLD"}, result);
        assertEquals(String[].class, result.getClass());
    }

    @Test
    void testIntegerFilter() {
        Integer[] input = {1, 2, 3};
        Filter<Integer> doubleValue = n -> n * 2;

        Integer[] result = App.filter(input, doubleValue);

        assertArrayEquals(new Integer[]{2, 4, 6}, result);
        assertEquals(Integer[].class, result.getClass());
    }

    @Test
    void testNullElements() {
        String[] input = {null, "test"};
        Filter<String> safeToUpper = s -> s != null ? s.toUpperCase() : null;

        String[] result = App.filter(input, safeToUpper);

        assertArrayEquals(new String[]{null, "TEST"}, result);
    }
}
