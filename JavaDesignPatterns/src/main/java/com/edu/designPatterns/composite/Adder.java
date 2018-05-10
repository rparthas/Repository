package com.edu.designPatterns.composite;

public class Adder extends BinaryExpression {
    public Adder(Expression left, Expression right) {
        super(left, right);
    }

    public double getValue() {
        return left.getValue() + right.getValue();
    }
}
