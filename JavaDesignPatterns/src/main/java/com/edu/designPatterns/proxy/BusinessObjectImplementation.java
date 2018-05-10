package com.edu.designPatterns.proxy;

public class BusinessObjectImplementation extends BusinessObject {
    @Override
    public void sayHi() {
        System.out.println("Hello! Have a nice day!");
    }
}
