package org.frank.hibernate.inheritance.models.union;

public class Vehicle {

    
    private String id;
    
    private Integer speed;// 速度  

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
