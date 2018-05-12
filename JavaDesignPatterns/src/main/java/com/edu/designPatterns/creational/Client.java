package com.edu.designPatterns.creational;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private List<Shape> shapes;

    public Client() {
        this.shapes = new ArrayList<Shape>();
    }

    public void readShapes(String[] shapesAsText) {
        for (String shapeAsText : shapesAsText) {
            this.shapes.add(Shape.create(shapeAsText));
        }
    }

    public void drawAllShapes() {
        for (Shape s : this.shapes) {
            s.draw();
        }
    }
}
