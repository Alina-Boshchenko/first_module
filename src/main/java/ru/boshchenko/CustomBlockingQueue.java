package ru.boshchenko;

import java.util.LinkedList;
import java.util.Queue;

public class CustomBlockingQueue<T> {

    private final Queue<T> elements = new LinkedList<>();
    private final int capacity;

    public CustomBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }

    public synchronized void enqueue(T element) throws InterruptedException {
        while (elements.size() == capacity) {
            wait();
        }
        elements.offer(element);
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (elements.isEmpty()) {
            wait();
        }
        T element = elements.remove();
        notifyAll();
        return element;
    }

    public synchronized int size() {
        return elements.size();
    }
}
