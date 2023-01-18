package org.frank.hibernate.collections.models;

import java.io.Serializable;
import java.util.Set;

/**
 * 1. Set是无序，不可重复的集合。 
 * 
 * */
public class SysUser implements Serializable {

    private Long id; 
    private String name;
    private Set<String> addressSet;

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

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addressSet=" + addressSet +
                '}';
    }
}
