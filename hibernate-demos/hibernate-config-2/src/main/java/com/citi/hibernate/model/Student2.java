package com.citi.hibernate.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Student2")
@Table(name = "t_student2")
public class Student2 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6816708945014345912L;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = false)
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name",length=10)
	private String name;
	
	@Column(name = "sex")
	private String sex;
	
	@Column(name="text_content", columnDefinition="TEXT", nullable=true)
	private String textContent;
	
	@Column(name="my_blob", columnDefinition="mediumblob", nullable=true)
	private Blob blob;
	
	@Column(name = "birthday")
	private Date birthday;
	
	@Formula("(select floor(datediff(now(),s.birthday)/365.25) from student2 s where s.id=id)")
	//@Formula(("select count(*) from t_student2"))
	private int age;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public Student2(String name, String sex, String textContent, Blob blob, Date birthday, int age) {
		super();
		this.name = name;
		this.sex = sex;
		this.textContent = textContent;
		this.blob = blob;
		this.birthday = birthday;
		this.age = age;
	}
	public Student2() {
		super();
	}
	@Override
	public String toString() {
		return "Student2 [name=" + name + ", sex=" + sex + ", textContent=" + textContent + ", blob=" + blob
				+ ", birthday=" + birthday + ", age=" + age + "]";
	}		
}
