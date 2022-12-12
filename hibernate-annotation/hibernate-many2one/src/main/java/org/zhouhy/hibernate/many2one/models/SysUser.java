package org.zhouhy.hibernate.many2one.models;

import javax.persistence.*;

@Entity(name = "SysUser")
@Table(name = "t1_sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USER_CODE", nullable = false, length = 40)
    private String userCode;

    @Column(name = "USER_NAME", length = 60)
    private String userName;

    @Column(name = "DEPT_ID", insertable = false, updatable = false, length = 40)
    private String deptId;

    @ManyToOne
    @JoinColumn(name = "DEPT_ID", nullable=false)
    private SysOrg dept;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public SysOrg getDept() {
        return dept;
    }

    public void setDept(SysOrg dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", deptId='" + deptId + '\'' +
                '}';
    }
}
