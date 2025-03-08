package ru.boshchenko;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void constructor_InvalidCapacity_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new CustomBlockingQueue<>(0));
        assertThrows(IllegalArgumentException.class, () -> new CustomBlockingQueue<>(-5));
    }

    @Test
    public void basicEnqueueDequeue() throws InterruptedException {
        CustomBlockingQueue<Integer> queue = new CustomBlockingQueue<>(3);

        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.size());

        assertEquals(1, queue.dequeue());
        assertEquals(1, queue.size());
    }

    @Test
    @Timeout(2)
    public void enqueueBlocksWhenFull() throws Exception {
        CustomBlockingQueue<Integer> queue = new CustomBlockingQueue<>(1);
        queue.enqueue(42);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                queue.enqueue(99);
            } catch (InterruptedException ignor) {
            }
        });
        Thread.sleep(500);
        assertFalse(future.isDone());
        queue.dequeue();

        assertDoesNotThrow(() -> future.get(500, TimeUnit.MILLISECONDS));
        assertEquals(1, queue.size());
        executor.shutdown();
    }

    @Test
    @Timeout(2)
    public void dequeueBlocksWhenEmpty() throws Exception {
        CustomBlockingQueue<Integer> queue = new CustomBlockingQueue<>(1);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(queue::dequeue);

        Thread.sleep(500);
        assertFalse(future.isDone());
        queue.enqueue(42);

        assertEquals(42, future.get(500, TimeUnit.MILLISECONDS));
        executor.shutdown();
    }

    @Test
    public void maintainsFifoOrder() throws InterruptedException {
        CustomBlockingQueue<String> queue = new CustomBlockingQueue<>(3);

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());
    }

    @Test
    @Timeout(5)
    public void concurrentProducerConsumer() throws Exception {
        final int totalElements = 1000;
        CustomBlockingQueue<Integer> queue = new CustomBlockingQueue<>(10);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(2);

        executor.submit(() -> {
            try {
                for (int i = 0; i < totalElements; i++) {
                    queue.enqueue(i);
                }
                latch.countDown();
            } catch (InterruptedException ignor) {}
        });

        executor.submit(() -> {
            try {
                for (int i = 0; i < totalElements; i++) {
                    assertEquals(i, queue.dequeue());
                }
                latch.countDown();
            } catch (InterruptedException ignor) {}
        });

        latch.await();
        executor.shutdown();
        assertTrue(isEmpty(queue));
    }

    private boolean isEmpty(CustomBlockingQueue<?> queue) {
        return queue.size() == 0;
    }
}
