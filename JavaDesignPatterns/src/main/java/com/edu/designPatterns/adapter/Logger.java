package com.edu.designPatterns.adapter;

public abstract class Logger {

    public static Logger create() {
        return new ApacheCommonsLogAdapter();
    }

    abstract public void trace(String str);

    abstract public void debug(String str);

    abstract public void log(String str);
}