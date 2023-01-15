package org.frank.hibernate.inheritance.models.union;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t1_car")
public class Car extends Vehicle{
    
    @Column(name = "ENGINE")
    private String engine;// 发动机 

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
