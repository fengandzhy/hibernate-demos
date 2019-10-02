package org.zhouhy.hibernate.model.collection.set;

import java.util.HashSet;
import java.util.Set;

public class User {
	private int id;
	private String userName;
	private Set<String> address = new HashSet<>();
	
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
	public Set<String> getAddress() {
		return address;
	}
	public void setAddress(Set<String> address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", address=" + address + "]";
	}	
}
