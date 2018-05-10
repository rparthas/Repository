package com.edu.designPatterns.decorator;

import java.util.LinkedList;
import java.util.List;

public class TextField implements GraphicalComponent {

    private List<String> lines = new LinkedList<String>();

    @Override
    public void paint() {
        System.out.println("Start TextField.....");
        for (String l : this.lines) {
            System.out.println(l);
        }
        System.out.println("End TextField.....");
    }

    @Override
    public GraphicalComponent addContent(Object l) {
        assert (l instanceof String);
        this.lines.add((String) l);
        if (this.lines.size() == 3) {
            // add the scroll bar decorator???
            return new ScrollBarDecorator(this);
        }
        return this;
    }
}
