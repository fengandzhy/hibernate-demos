package org.zhouhy.hibernate.many2one.models;

import javax.persistence.*;

@Entity(name = "SysUser")
@Table(name = "t1_sys-user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    
}
