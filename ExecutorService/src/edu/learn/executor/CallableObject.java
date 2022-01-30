package edu.learn.executor;

import java.util.concurrent.Callable;

public class CallableObject implements Callable<String> {

    public int loop = 0;

    public CallableObject(int i) {
        this.loop = i;
    }

    @Override
    public String call() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("Thread %s finished running with %s", loop, Util.printStats());
    }
}
