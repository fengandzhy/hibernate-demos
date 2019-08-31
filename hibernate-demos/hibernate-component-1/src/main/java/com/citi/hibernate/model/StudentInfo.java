package com.citi.hibernate.model;

import java.util.Date;

public class StudentInfo {
	private String address;
	private Date birthday;
	private String classes;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public StudentInfo() {
		super();
	}
	@Override
	public String toString() {
		return "StudentInfo [address=" + address + ", birthday=" + birthday + ", classes=" + classes + "]";
	}	
}
