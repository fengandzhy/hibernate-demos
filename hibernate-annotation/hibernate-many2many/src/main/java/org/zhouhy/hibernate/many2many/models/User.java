package org.zhouhy.hibernate.many2many.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "User")
@Table(name = "t1_user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

//    @Column(name = "username", unique=true)
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    
//    @ManyToMany(targetEntity=Role.class,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @ManyToMany(targetEntity=Role.class)
    @JoinTable(name="t1_user_role",  // t1_user_role 中间表的表名          
            joinColumns = @JoinColumn(referencedColumnName="Id",name="user_id"),// Id 是 User对应表 主键的列名, user_id 中间表中关联User的列名.
            inverseJoinColumns = @JoinColumn(referencedColumnName="Id",name="role_id")) // Id 是Role对应表 主键列的名称, role_id 是中间表中关联Role的列名.
    @Cascade(value= org.hibernate.annotations.CascadeType.SAVE_UPDATE)
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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
