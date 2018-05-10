package com.edu.designPatterns.proxy;

public class BusinessObjectProxy extends BusinessObject {


    BusinessObjectImplementation businessObjectImplementation = null;


    public BusinessObjectProxy(BusinessObjectImplementation businessObjectImplementation) {
        super();
        this.businessObjectImplementation = businessObjectImplementation;
    }

    @Override
    public void sayHi() {
        this.businessObjectImplementation.sayHi();
    }
}
