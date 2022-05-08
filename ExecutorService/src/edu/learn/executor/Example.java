package edu.learn.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Example {
    public static void main(String... args) throws InterruptedException {
        System.out.println("Started:" + Util.printStats());
//        threads();
//        executorService(Executors.newFixedThreadPool(10), true, 1000);
//        executorService(Executors.newSingleThreadExecutor(), false, 100);
//        executorService(Executors.newCachedThreadPool(), false, 1000);
//        executorServiceWithShutdown();
//        executorServiceWithFuture();
//        executorServiceWithFutureCall(true);
//        executorServiceWithFutureCall(false);
//        executorServiceWithSchedule();
//        executorService(Executors.newWorkStealingPool(), true, 1000);
        System.out.println("Completed:" + Util.printStats());
    }

    private static void threads() {
        for (int i = 0; i < 1000; i++) {
            Runner runner = new Runner(i + 1);
            runner.start();
        }
    }

    private static void executorService(ExecutorService es, boolean await, int counter) throws InterruptedException {
        for (int i = 0; i < counter; i++) {
            Runner runner = new Runner(i + 1);
            es.execute(runner);
        }
        es.shutdown();
        if (await) {
            es.awaitTermination(100, TimeUnit.SECONDS);
        }
    }

    private static void executorServiceWithShutdown() throws InterruptedException {
        ExecutorService es = null;
        es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            Runner runner = new Runner(i + 1);
            es.execute(runner);
        }
        List<Runnable> tasks = es.shutdownNow();
        System.out.println("Tasks Length:" + tasks.size());
    }

    private static void executorServiceWithFuture() {
        ExecutorService es = Executors.newWorkStealingPool();
        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Runner runner = new Runner(i + 1);
            futures.add((Future<Void>) es.submit(runner));
        }
        for (Future<Void> future : futures) {
            while (!future.isDone()) {
            }
        }
    }


    private static void executorServiceWithFutureCall(boolean invokeOne) {
        ExecutorService es = Executors.newWorkStealingPool();
        List<Callable<String>> callables = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            callables.add(new CallableObject(i + 1));
        }
        try {
            if (invokeOne) {
                System.out.println(es.invokeAny(callables));
            } else {
                for (Future<String> future : es.invokeAll(callables)) {
                    System.out.println(future.get());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void executorServiceWithSchedule() throws InterruptedException {
        ScheduledExecutorService es =
                Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 30; i++) {
            Runner runner = new Runner(i + 1);
            es.schedule(runner, 3, TimeUnit.SECONDS);
        }
        es.shutdown();
    }

}



