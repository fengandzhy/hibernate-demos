package org.frank.hibernate.collections.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stuff implements Serializable {

    private Long id; 
    private String name;

    private List<String> addressList = new ArrayList<String>(); 

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

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }
}
