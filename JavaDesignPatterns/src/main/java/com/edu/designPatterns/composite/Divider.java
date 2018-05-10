package com.edu.designPatterns.composite;

public class Divider extends BinaryExpression {
    public Divider(Expression left, Expression right) {
        super(left, right);
    }

    public double getValue() {
        return left.getValue() / right.getValue();
    }
}
