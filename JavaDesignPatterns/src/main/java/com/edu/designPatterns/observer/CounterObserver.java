package com.edu.designPatterns.observer;

import java.util.Observable;
import java.util.Observer;

public class CounterObserver implements Observer {

    public CounterObserver(Counter counter) {
        counter.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof Counter) {
            Counter counter = (Counter) observable;
            System.out.println("Counter value changed: " + counter.getCount());
        }
    }
}
