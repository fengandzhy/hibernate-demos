package org.frank.hibernate.collections.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 1. Set是无序，不可重复的集合。 这里的集合映射必须是基本数据类型或者String或者Date类型的, 不能是其他复杂类型的
 * 例如本例中 private Set<Info> infoSet; 这个就无法映射, 这就要用到一对多这种映射关系还要映射 Info.
 * 
 * */
@Entity
@Table(name = "t1_sys_user")
public class SysUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "user1_address_set", joinColumns = @JoinColumn(name = "sys_user_Id"))
    @Column(name = "address")
//    @OrderColumn(name="address ASC")    
    private Set<String> addressSet;

    @ElementCollection
    @CollectionTable(name = "user1_date_set", joinColumns = @JoinColumn(name = "sys_user_Id"))
    @Column(name = "date")
//    @OrderColumn(name="date ASC")
    private Set<Date> dateSet; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getAddressSet() {
        return addressSet;
    }

    public void setAddressSet(Set<String> addressSet) {
        this.addressSet = addressSet;
    }

//    public Set<Info> getInfoSet() {
//        return infoSet;
//    }
//
//    public void setInfoSet(Set<Info> infoSet) {
//        this.infoSet = infoSet;
//    }

    public Set<Date> getDateSet() {
        return dateSet;
    }

    public void setDateSet(Set<Date> dateSet) {
        this.dateSet = dateSet;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addressSet=" + addressSet +
                '}';
    }
}
