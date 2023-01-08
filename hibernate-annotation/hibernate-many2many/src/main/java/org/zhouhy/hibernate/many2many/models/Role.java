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

    
    @ManyToMany(targetEntity=User.class) 
    @JoinTable(name="t1_user_role", // t1_user_role 中间表的表名
            joinColumns = @JoinColumn(referencedColumnName="Id",name="role_id"), // Id 是Role 对应表的主键列的名称, role_id 是中间表中关联Role的列名.
            inverseJoinColumns = @JoinColumn(referencedColumnName="Id",name="user_id"))// Id 是 User对应表 主键的列名, user_id 中间表中关联User的列名.
 //   @ManyToMany(targetEntity=User.class,mappedBy="roles") // 这里的roles 表示在 User 类中的与Role关联的属性名.
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
