package com.edu.designPatterns.test;

import com.edu.designPatterns.decorator.BorderDecorator;
import com.edu.designPatterns.decorator.GraphicalComponent;
import com.edu.designPatterns.decorator.TextField;

public class TestDecorator {

    public static void main(String[] args) {
        GraphicalComponent tf = construct2LineTB();
        // Add a border to the text field here
        tf = new BorderDecorator(tf);
        tf.paint();
    }

    public static GraphicalComponent construct2LineTB() {
        GraphicalComponent tf = new TextField();
        tf = tf.addContent("To move, to breathe, to fly, to float,");
        tf = tf.addContent("To gain all while you give,");
        return tf;
    }

    public static GraphicalComponent constructHCAndersenPoem() {
        GraphicalComponent tf = new TextField();
        tf = tf.addContent("To move, to breathe, to fly, to float,");
        tf = tf.addContent("To gain all while you give,");
        tf = tf.addContent("To roam the roads of lands remote,");
        tf = tf.addContent("To travel is to live.");
        tf = tf.addContent("-- H.C. Andersen");
        return tf;
    }
}
