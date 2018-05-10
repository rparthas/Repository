package com.edu.designPatterns.composite;

public class Subtractor extends BinaryExpression {

    public Subtractor(Expression left, Expression right) {
        super(left, right);
    }

    public double getValue() {
        return left.getValue() - right.getValue();
    }
}
