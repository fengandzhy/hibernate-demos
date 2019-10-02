package org.zhouhy.hibernate.model.collection.list;

import java.util.ArrayList;
import java.util.List;


public class User {
	private int id;
	private String userName;
	private List<String> address = new ArrayList<>();
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
	public List<String> getAddress() {
		return address;
	}
	public void setAddress(List<String> address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", address=" + address + "]";
	}	
}
