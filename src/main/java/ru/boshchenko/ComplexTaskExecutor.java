package ru.boshchenko;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ComplexTaskExecutor {

    private final int numberOfPool;

    public ComplexTaskExecutor(int numberOfPool) {
        this.numberOfPool = numberOfPool;
    }

    public void executeTasks(int numberOfTasks) {

        List<ComplexTask> listTask = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfPool);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(numberOfTasks, () -> {
            int commonResult = listTask.stream().mapToInt(ComplexTask::getResultOfTask).sum();
            System.out.println("Все потоки достигли барьера. Результат работы: " + commonResult);
        });

        for (int i = 0; i < numberOfTasks; i++) {
            ComplexTask complexTask = new ComplexTask();
            listTask.add(complexTask);

            executorService.submit(() -> {
                complexTask.execute();
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException("отловлено исключение: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
