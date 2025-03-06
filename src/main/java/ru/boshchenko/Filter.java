package ru.boshchenko;

@FunctionalInterface
public interface Filter<T> {
    T apply(T object);
}
