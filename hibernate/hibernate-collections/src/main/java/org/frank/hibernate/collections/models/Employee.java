package org.frank.hibernate.collections.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Employee implements Serializable {
    private Long id;
    private String name;
    private Map<String, String> addressMap = new HashMap<String, String>();

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

    public Map<String, String> getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(Map<String, String> addressMap) {
        this.addressMap = addressMap;
    }
}
