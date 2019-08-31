package com.citi.hibernate.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Course")
@Table(name = "t_course")
public class Course implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7050548767409468116L;
	
	@Id
	@GeneratedValue(generator = "aGenerator")
	@GenericGenerator(name = "aGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "name")	
	private String name;
	
	//@ManyToMany(mappedBy="courses")
	@ManyToMany
    @JoinTable(name="t_teachars_courses",                    //中间表的名字
            joinColumns= {@JoinColumn(name="course_id")},        //外键的字段
            inverseJoinColumns= {@JoinColumn(name="teacher_id")})    //反转控制字段的名字
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
