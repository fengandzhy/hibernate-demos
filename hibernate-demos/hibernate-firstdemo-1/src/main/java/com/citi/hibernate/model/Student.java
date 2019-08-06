package com.citi.hibernate.model;

import java.util.Date;

/**
 * ±ØÐë×ñÊØjavabean¹æ·¶
 * */
public class Student {
	private int id;
	private String name;
	private String sex;
	private Date regDate;
	
	public Student() {
		super();
	}

	public Student(String name, String sex, Date regDate) {
		super();
		this.name = name;
		this.sex = sex;
		this.regDate = regDate;
	}

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

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", sex=" + sex + ", regDate=" + regDate + "]";
	}	
	
}
