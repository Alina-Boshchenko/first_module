package ru.boshchenko;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComplexTaskExecutorIntegrationTest {

    @Test
    void executeTasksShouldPrintMergedResult() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        ComplexTaskExecutor executor = new ComplexTaskExecutor(5);
        executor.executeTasks(5);

        System.setOut(originalOut);
        String output = outputStream.toString();
        assertTrue(output.contains("Все потоки достигли барьера. Результат работы: "));
    }
}