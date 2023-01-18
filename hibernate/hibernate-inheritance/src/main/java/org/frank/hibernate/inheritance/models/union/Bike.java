package org.frank.hibernate.inheritance.models.union;




public class Bike extends Vehicle{

    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
