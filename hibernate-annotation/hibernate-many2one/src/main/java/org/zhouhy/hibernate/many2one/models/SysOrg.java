package org.zhouhy.hibernate.many2one.models;

import javax.persistence.*;

@Entity(name = "SysOrg")
@Table(name = "t1_sys-org")
public class SysOrg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ORG_NAME", length = 60)
    private String orgName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
