package org.zhouhy.hibernate.many2one.models;

import javax.persistence.*;

@Entity(name = "SysOrg")
@Table(name = "t1_sys-org")
public class SysOrg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
