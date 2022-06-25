package org.zhouhy.hibernate.models;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity(name = "Member")
@Table(name = "t1_member")
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;

    @Column(name="text_content", columnDefinition="TEXT", nullable=true)
    private String textContent;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @Column(name="my_blob", columnDefinition="mediumblob", nullable=true)
    private Blob myBlob;

    @Column(name = "birthday")
    private Date birthday;

    @Formula("(select floor(datediff(now(),s.birthday)/365.25) from t1_member s where s.id=id)")
    private int age;

    public Member(){
        
    }

    public Member(String name, String sex, String textContent, Blob myBlob, Date birthday, int age){
        super();
        this.name = name;
        this.sex = sex;
        this.textContent = textContent;
        this.myBlob = myBlob;
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

    public Blob getMyBlob() {
        return myBlob;
    }

    public void setMyBlob(Blob myBlob) {
        this.myBlob = myBlob;
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

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", textContent='" + textContent + '\'' +
                ", myBlob=" + myBlob +
                ", birthday=" + birthday +
                ", age=" + age +
                '}';
    }
}
