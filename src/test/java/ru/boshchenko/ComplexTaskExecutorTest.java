package ru.boshchenko;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ComplexTaskExecutorTest {

    @Test
    void executeTasksShouldCompleteAllTasks() throws InterruptedException {
        ComplexTaskExecutor executor = new ComplexTaskExecutor(5);

        executor.executeTasks(5);

        assertDoesNotThrow(() -> executor.executeTasks(5));
    }

    @Test
    void cyclicBarrierShouldMergeResults() {
        try (MockedConstruction<ComplexTask> mockedTasks = mockConstruction(ComplexTask.class, (mock, context) -> {
            when(mock.getResultOfTask()).thenReturn(2);
        })) {
            ComplexTaskExecutor executor = new ComplexTaskExecutor(5);
            executor.executeTasks(5);

            List<ComplexTask> tasks = mockedTasks.constructed();
            assertEquals(5, tasks.size());

            tasks.forEach(task -> verify(task).execute());
        }
    }
}