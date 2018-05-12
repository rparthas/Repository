package com.edu.designPatterns.test;

import com.edu.designPatterns.creational.Logger;

public class TestSingleton {

    public static void main(String[] args) {
        Logger lm = Logger.getInstance();
        lm.log("Some message");
    }
}
