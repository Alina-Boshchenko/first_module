package ru.boshchenko;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexTaskTest {

    @Test
    void executeShouldSetResultInRange() {
        ComplexTask task = new ComplexTask();
        task.execute();
        int result = task.getResultOfTask();
        assertTrue(result >= 0 && result < 10);
    }

    @Test
    void getResultOfTaskShouldReturnCorrectValue() {
        ComplexTask task = new ComplexTask();
        task.execute();
        int expected = task.getResultOfTask();
        assertEquals(expected, task.getResultOfTask());
    }
}
