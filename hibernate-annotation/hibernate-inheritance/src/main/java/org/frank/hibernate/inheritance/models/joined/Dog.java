package org.frank.hibernate.inheritance.models.joined;

import javax.persistence.*;

@Entity
@Table(name = "t1_dog")
@PrimaryKeyJoinColumn(name = "DOG_ID")
public class Dog extends Animal{

//    @Id
//    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    
    @Column(name = "LEGS")
    private Integer legs;

    public Integer getLegs() {
        return legs;
    }

    public void setLegs(Integer legs) {
        this.legs = legs;
    }

//    @Override
//    public Integer getId() {
//        return id;
//    }
//
//    @Override
//    public void setId(Integer id) {
//        this.id = id;
//    }
}
