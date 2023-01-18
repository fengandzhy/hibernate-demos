package org.frank.hibernate.inheritance.models.joined;


public class Dog extends Animal{    
    
    private Integer legs;

    public Integer getLegs() {
        return legs;
    }

    public void setLegs(Integer legs) {
        this.legs = legs;
    }


}
