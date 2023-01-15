package org.frank.hibernate.inheritance.models.single;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


public class Document extends WindowFile{

    
    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Document{" +
                "size='" + size + '\'' +
                '}';
    }
}
