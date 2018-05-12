package com.edu.designPatterns.test;

import com.edu.designPatterns.creational.Client;

public class TestCreational {
    public static void main(String[] args) {
        String [] shapes = {
                "#rect 1,2,100,200",
                "#oval 1,1,100,100",
                "#rect 100,200,1,1",
                "#line 1,2,3,4"
        };
        Client c = new Client();
        c.readShapes(shapes);
        c.drawAllShapes();
    }
}
