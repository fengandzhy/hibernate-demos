package org.zhouhy.hibernate.c3p0.models;

public class User {
	private long id;
	private String username;
	private String password;
	
	public User() {
		super();
	}	
	public User(String username, String password) {
		super();		
		this.username = username;
		this.password = password;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}	
}
