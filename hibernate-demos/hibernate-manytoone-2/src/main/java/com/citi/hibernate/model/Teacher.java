package com.citi.hibernate.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Teacher")
@Table(name = "t_teacher")
public class Teacher implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6261916060803684550L;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	
	/**
	 * test
	 * */
//	@OneToMany
//	@JoinColumn(name="teacher_id")
	@OneToMany(mappedBy="teacher")	
	private Set<Student> students = new HashSet<>();	
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
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
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + "]";
	}	
}	
