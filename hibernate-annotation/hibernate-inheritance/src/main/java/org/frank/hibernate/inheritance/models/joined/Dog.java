package org.frank.hibernate.inheritance.models.joined;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "t1_dog")
@PrimaryKeyJoinColumn(name = "DOG_ID")
public class Dog extends Animal{

    @Column(name = "LEGS")
    private Integer legs;

    public Integer getLegs() {
        return legs;
    }

    public void setLegs(Integer legs) {
        this.legs = legs;
    }
}
