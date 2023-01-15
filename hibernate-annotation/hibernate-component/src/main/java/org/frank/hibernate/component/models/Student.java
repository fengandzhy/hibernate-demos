package org.frank.hibernate.component.models;

import javax.persistence.*;

@Entity
@Table(name="t1_stu")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "address", column = @Column(name = "address")),
            @AttributeOverride( name = "email", column = @Column(name = "email")),
            @AttributeOverride( name = "birthday", column = @Column(name = "birthday"))
    })
    private StudentInfo info;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentInfo getInfo() {
        return info;
    }

    public void setInfo(StudentInfo info) {
        this.info = info;
    }
}
