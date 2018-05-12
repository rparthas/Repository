package com.edu.designPatterns.creational;

public interface Shape {
    static Shape create(String s) {
        Shape retValue = null;
        if (s.startsWith("#rect")) {
            retValue = new Rectangle();
        } else if (s.startsWith("#oval")) {
            retValue = new Oval();
        } else if (s.startsWith("#line")) {
            retValue = new Line();
        }
        return retValue;
    }

    void draw();
}
