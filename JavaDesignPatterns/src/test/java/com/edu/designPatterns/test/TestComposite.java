package com.edu.designPatterns.test;


import com.edu.designPatterns.composite.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestComposite {

    @Test
    public void testConstant() {
        Expression expression = new Constant(1);
        assertEquals(1, expression.getValue(), 0.0);
    }

    @Test
    public void testAdder() {
        Expression expression = new Adder(new Constant(2), new Constant(3));
        assertEquals(5, expression.getValue(), 0.0);
    }

    @Test
    public void testSubtractor() {
        Expression expression = new Subtractor(new Constant(2), new Constant(3));
        assertEquals(-1, expression.getValue(), 0.0);
    }

    @Test
    public void testMultiplier() {
        Expression expression = new Multiplier(new Constant(2), new Constant(3));
        assertEquals(6, expression.getValue(), 0.0);
    }

    @Test
    public void testDivider() {
        Expression expression = new Divider(new Constant(6), new Constant(3));
        assertEquals(2, expression.getValue(), 0.0);
    }

    @Test
    public void testCombination() {
        Constant num2 = new Constant(2);
        Constant num3 = new Constant(3);
        Expression expression = new Divider(new Multiplier(num2, num3), num2);
        assertEquals(expression.getValue(), num3.getValue(), 0.0);
    }


}
