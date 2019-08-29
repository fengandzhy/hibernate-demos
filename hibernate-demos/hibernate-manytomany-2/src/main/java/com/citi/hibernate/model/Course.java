package com.citi.hibernate.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Course implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7050548767409468116L;
	private int id;
	private String name;
	private Set<Teacher> teachers = new HashSet<>();	
	public Course() {
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
	public Set<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}	
}
