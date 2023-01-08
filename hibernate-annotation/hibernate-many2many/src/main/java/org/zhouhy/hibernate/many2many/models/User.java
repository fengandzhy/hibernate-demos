package org.zhouhy.hibernate.many2many.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "User")
@Table(name = "t1_user")
public class User {
    
    @Id@Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", unique=true)
    private String username;

    @Column(name = "password")
    private String password;

    
    @ManyToMany    
    @JoinTable(name="t1_user_role",            
            joinColumns = @JoinColumn(referencedColumnName="user_id",name="userid"),
            inverseJoinColumns = @JoinColumn(referencedColumnName="role_id",name="roleid"))
    private Set<Role> roles = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
