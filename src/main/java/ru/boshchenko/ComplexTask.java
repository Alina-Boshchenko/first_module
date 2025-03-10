package ru.boshchenko;

public class ComplexTask {

    private int resultOfTask;

    public void execute() {
        System.out.println("Выполняется часть задачи в потоке - " + Thread.currentThread().getName());
        resultOfTask = (int) (Math.random() * 10);
    }

    public int getResultOfTask() {
        return resultOfTask;
    }
}
