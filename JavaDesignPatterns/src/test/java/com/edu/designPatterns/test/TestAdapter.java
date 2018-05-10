package com.edu.designPatterns.test;

import com.edu.designPatterns.adapter.Logger;

public class TestAdapter {
    public static void main(String[] args) {
        Logger logger = Logger.create();
        logger.debug("Some debug message");
        logger.trace("Some trace message");
        logger.log("Some log message");
    }
}
