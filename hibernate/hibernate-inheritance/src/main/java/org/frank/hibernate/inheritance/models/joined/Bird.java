package org.frank.hibernate.inheritance.models.joined;


public class Bird extends Animal{    
    
    private String speed;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
