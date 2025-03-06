package ru.boshchenko;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    @DisplayName("Подсчет элементов в массиве с повторениями")
    void testCountElementsWithDuplicates() {
        String[] input = {"apple", "banana", "apple"};
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 2);
        expected.put("banana", 1);

        Map<String, Integer> result = App.countOfElements(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Подсчет элементов с уникальными значениями")
    void testCountUniqueElements() {
        Integer[] input = {1, 2, 3};
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(1, 1);
        expected.put(2, 1);
        expected.put(3, 1);

        Map<Integer, Integer> result = App.countOfElements(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Пустой массив должен возвращать пустую карту")
    void testEmptyArray() {
        String[] input = {};
        Map<String, Integer> result = App.countOfElements(input);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Массив с одним элементом")
    void testSingleElementArray() {
        String[] input = {"hello"};
        Map<String, Integer> expected = new HashMap<>();
        expected.put("hello", 1);

        Map<String, Integer> result = App.countOfElements(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Обработка null в массиве")
    void testNullElements() {
        String[] input = {null, "test", null};
        Map<String, Integer> expected = new HashMap<>();
        expected.put(null, 2);
        expected.put("test", 1);

        Map<String, Integer> result = App.countOfElements(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Передача null вместо массива вызывает исключение")
    void testNullArray() {
        assertThrows(NullPointerException.class, () -> {
            App.countOfElements(null);
        });
    }

    @Test
    @DisplayName("Пользовательские объекты в качестве ключей")
    void testCustomObjects() {
        class CustomKey {
            final int id;

            CustomKey(int id) {
                this.id = id;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                CustomKey key = (CustomKey) o;
                return id == key.id;
            }

            @Override
            public int hashCode() {
                return id;
            }
        }

        CustomKey[] input = {new CustomKey(1), new CustomKey(1), new CustomKey(2)};
        Map<CustomKey, Integer> expected = new HashMap<>();
        expected.put(input[0], 2);
        expected.put(input[2], 1);

        Map<CustomKey, Integer> result = App.countOfElements(input);
        assertEquals(expected, result);
    }
}
