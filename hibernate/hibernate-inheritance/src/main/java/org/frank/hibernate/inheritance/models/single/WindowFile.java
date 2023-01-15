package org.frank.hibernate.inheritance.models.single;

import javax.persistence.*;
import java.util.Date;


public class WindowFile {

  
    private Integer id;

    
    private String name;

   
    private String type;
  
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WindowFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }
}
