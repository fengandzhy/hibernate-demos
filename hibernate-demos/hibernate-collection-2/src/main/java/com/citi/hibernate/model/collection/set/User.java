package com.citi.hibernate.model.collection.set;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_user")
public class User {
	
	@Id
	@GeneratedValue(generator = "aGenerator")
	@GenericGenerator(name = "aGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "user_name")	
	private String userName;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@JoinTable(name = "t_address",joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "address")	
	private Set<String> addresses = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<String> addresses) {
		this.addresses = addresses;
	}	

}
