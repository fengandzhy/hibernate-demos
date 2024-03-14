package org.frank.hibernate.inheritance.models.union;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "t1_vehicle")
public class Vehicle {

    @Id
    // @GeneratedValue  
//    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "xxx") // 这里的xxx 只是一个名字 可以随意起, 只要确保和下面的名字相同.
    @GenericGenerator(name = "xxx", strategy = "uuid2")
    private String id;

    @Column(name = "SPEED")
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
