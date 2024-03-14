package org.frank.hibernate.inheritance.models.joined;

import javax.persistence.*;

@Entity
@Table(name = "t1_dog")
@PrimaryKeyJoinColumn(name = "DOG_ID") // 字表中关联主表的外键
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
