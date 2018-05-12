package com.edu.designPatterns.observer;

import java.util.Observable;

public class Counter extends Observable {

    int count =0;

    public void increment(){
        count++;
        this.setChanged();
        this.notifyObservers();
    }

    public int getCount() {
        return count;
    }
}
