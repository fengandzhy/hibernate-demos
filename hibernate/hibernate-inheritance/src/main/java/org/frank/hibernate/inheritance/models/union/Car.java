package org.frank.hibernate.inheritance.models.union;

public class Car extends Vehicle{
    
    
    private String engine;// 发动机 

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
