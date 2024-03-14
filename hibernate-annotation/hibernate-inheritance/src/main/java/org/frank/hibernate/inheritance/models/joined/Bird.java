package org.frank.hibernate.inheritance.models.joined;

import javax.persistence.*;

@Entity
@Table(name = "t1_bird")
@PrimaryKeyJoinColumn(name = "BIRD_ID")
public class Bird extends Animal{
    
    @Column(name = "SPEED")
    private String speed;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
