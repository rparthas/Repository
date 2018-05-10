package com.edu.designPatterns.decorator;

public interface GraphicalComponent {
    public void paint();

    public GraphicalComponent addContent(Object content);
}
