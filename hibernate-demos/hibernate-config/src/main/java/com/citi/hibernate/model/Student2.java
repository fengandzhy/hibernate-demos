package com.citi.hibernate.model;

import java.sql.Blob;
import java.util.Date;

/**
 * 必须遵守javabean规范,这个类一定不能是final类，
 * 因为hibernate有可能需要这个类的代理,产生这个类的子类
 * */
public class Student2 {
	private int id;
	private String name;
	private String sex;
	private String textContent;
	private Blob blob;
	private Date birthday;
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
