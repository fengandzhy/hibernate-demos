package org.zhouhy.hibernate.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Teacher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 529297022018967534L;
	private int id;
	private String name;
	private Set<Course> courses=new HashSet<>();
	
	public Teacher() {
		super();
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
	public Set<Course> getCourses() {
		return courses;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	
	
}
