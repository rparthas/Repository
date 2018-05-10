package com.edu.designPatterns.adapter;

public class LoggerImpl extends Logger {
    public void trace(String str) {
        System.out.print("TRACE: ");
        System.out.println(str);
    }

    public void debug(String str) {
        System.out.print("DEBUG: ");
        System.out.println(str);
    }

    public void log(String str) {
        System.out.print("LOG: ");
        System.out.println(str);
    }

}
