package org.zhouhy.hibernate.many2many.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Role")
@Table(name = "t1_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "username", unique=true)
    private String name;

    
    @ManyToMany 
    @JoinTable(name="t1_user_role",
            joinColumns = @JoinColumn(referencedColumnName="Id",name="role_id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName="Id",name="user_id"))
    private Set<User> users = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
