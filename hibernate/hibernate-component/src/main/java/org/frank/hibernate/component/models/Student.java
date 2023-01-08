package org.frank.hibernate.component.models;

public class Student {
    private long Id;
    private String name;
    private StudentInfo info;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentInfo getInfo() {
        return info;
    }

    public void setInfo(StudentInfo info) {
        this.info = info;
    }
}
