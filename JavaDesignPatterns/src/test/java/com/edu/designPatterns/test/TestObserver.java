package com.edu.designPatterns.test;

import com.edu.designPatterns.observer.Counter;
import com.edu.designPatterns.observer.CounterObserver;

import java.util.stream.IntStream;

public class TestObserver {

    public static void main(String[] args) {
        Counter counter = new Counter();
        CounterObserver observer = new CounterObserver(counter);
        for (int token = 1; token <= 10; token++) {
            counter.increment();
        }
    }
}
