package org.zhouhy.hibernate.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Teacher")
@Table(name = "t_teacher")
public class Teacher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 529297022018967534L;
	
	@Id
	@GeneratedValue(generator = "aGenerator")
	@GenericGenerator(name = "aGenerator", strategy = "native")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	
	@ManyToMany(mappedBy="teachers")
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
