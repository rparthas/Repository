package com.edu.designPatterns.composite;

public class Multiplier extends BinaryExpression {
    public Multiplier(Expression left, Expression right) {
        super(left, right);
    }

    public double getValue() {
        return left.getValue() * right.getValue();
    }
}
