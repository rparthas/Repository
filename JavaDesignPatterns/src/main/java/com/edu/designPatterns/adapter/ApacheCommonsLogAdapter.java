package com.edu.designPatterns.adapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;

public class ApacheCommonsLogAdapter extends Logger {
    private Log logger = new Log4JLogger("MyLogger");

    public void trace(String str) {
        logger.trace(str);
    }

    public void debug(String str) {
        logger.debug(str);
    }

    public void log(String str) {
        logger.info(str);
    }
}
