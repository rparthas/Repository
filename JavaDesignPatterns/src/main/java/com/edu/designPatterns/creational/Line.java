package com.edu.designPatterns.creational;

public class Line implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing " + this.getClass().getSimpleName());
    }
}
