package com.edu.designPatterns.decorator;

public class BorderDecorator extends Decorator {

    public BorderDecorator(GraphicalComponent next) {
        super(next);
    }

    @Override
    public void paint() {
        // could render here???
        System.out.println("Could have rendered here...");
        super.paint();
        System.out.println("Should probably render the border here...");
    }
}
