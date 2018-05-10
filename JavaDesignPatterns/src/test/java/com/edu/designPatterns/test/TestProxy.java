package com.edu.designPatterns.test;


import com.edu.designPatterns.proxy.BusinessObject;

public class TestProxy {
    public static void main(String[] args) {
        BusinessObject obj = BusinessObject.create();
        obj.sayHi();
    }
}
