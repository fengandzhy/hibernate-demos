package org.frank.hibernate.inheritance.models.joined;

import javax.persistence.*;

@Entity
@Table(name = "t1_bird")
@PrimaryKeyJoinColumn(name = "BIRD_ID")
public class Bird extends Animal{

//    @Id
//    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    
    @Column(name = "SPEED")
    private String speed;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
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
