package com.solvd.metro.functional;

@FunctionalInterface
public interface Processor<T> {
    void process(T item);
}
