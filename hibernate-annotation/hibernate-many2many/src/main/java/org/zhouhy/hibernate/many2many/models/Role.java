package org.zhouhy.hibernate.many2many.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Role")
@Table(name = "t1_role")
public class Role {
    @Id@Column(name="role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "username", unique=true)
    private String name;

    
    @ManyToMany 
    @JoinTable(name="t1_user_role",
            joinColumns = @JoinColumn(referencedColumnName="role_id",name="role_id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName="user_id",name="user_id"))
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
