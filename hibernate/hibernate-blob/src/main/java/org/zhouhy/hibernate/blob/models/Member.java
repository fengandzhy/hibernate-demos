package org.zhouhy.hibernate.blob.models;

import java.sql.Blob;
import java.util.Date;

public class Member {
    private long id;
    private String name;
    private String sex;
    private String textContent;
    private Blob blob;
    private Date birthday;
    private int age;

    public Member(){
        
    }

    public Member(String name,String sex,String textContent,Blob blob,Date birthday){
        super();
        this.name = name;
        this.sex = sex;
        this.textContent = textContent;
        this.blob = blob;
        this.birthday = birthday;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
