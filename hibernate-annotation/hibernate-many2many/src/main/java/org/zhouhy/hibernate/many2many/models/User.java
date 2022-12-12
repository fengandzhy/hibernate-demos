package org.zhouhy.hibernate.many2many.models;

import javax.persistence.*;

@Entity(name = "User")
@Table(name = "t1_user1")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique=true)
    private String username;

    @Column(name = "password")
    private String password;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
