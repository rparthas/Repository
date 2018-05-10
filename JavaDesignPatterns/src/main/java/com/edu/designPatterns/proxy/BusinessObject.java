package com.edu.designPatterns.proxy;

abstract public class BusinessObject {

    public static BusinessObject create() {
        return new BusinessObjectProxy(new BusinessObjectImplementation());
    }

    public abstract void sayHi();
}