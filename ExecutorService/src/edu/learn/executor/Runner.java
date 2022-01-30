package edu.learn.executor;

public class Runner extends Thread {

    public int loop = 0;

    public Runner(int i) {
        this.loop = i;
    }

    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String message = String.format("Thread %s finished running with %s", loop, Util.printStats());
        System.out.println(message);
    }

}
