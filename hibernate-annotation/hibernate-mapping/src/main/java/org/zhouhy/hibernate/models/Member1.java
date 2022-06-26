package org.zhouhy.hibernate.models;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Member1")
@Table(name = "t1_member")
public class Member1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;

    @Column(name="text_content", columnDefinition="TEXT", nullable=true)
    private String textContent;
    
    @Lob
    @Column( name = "my_blob",columnDefinition = "BLOB",nullable=true )
    private byte[] fileImage;

    @Column(name = "birthday")
    private Date birthday;

    @Formula("(select floor(datediff(now(),s.birthday)/365.25) from t1_member member where member.id=id)")
    private int age;

    public Member1(){

    }

    public Member1(String name, String sex, String textContent, byte[] fileImage, Date birthday, int age){
        super();
        this.name = name;
        this.sex = sex;
        this.textContent = textContent;
        this.fileImage = fileImage;
        this.birthday = birthday;
        this.age = age;
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

    public byte[] getFileImage() {
        return fileImage;
    }

    public void setFileImage(byte[] fileImage) {
        this.fileImage = fileImage;
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
