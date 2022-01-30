package edu.learn.executor;

import java.text.NumberFormat;

public class Util {

    public static String printStats() {
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();
        long freeMemory = runtime.freeMemory();
        return "free memory: " + format.format(freeMemory / 1024) + " ";
    }
}
