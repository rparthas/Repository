package com.edu.designPatterns.composite;

public abstract class BinaryExpression implements Expression {


    final Expression left;
    final Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
