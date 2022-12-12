package org.zhouhy.hibernate.many2many.models;

import javax.persistence.*;

@Entity(name = "Role")
@Table(name = "t1_role1")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
