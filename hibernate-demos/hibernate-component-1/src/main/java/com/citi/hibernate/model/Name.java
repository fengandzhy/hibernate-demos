package com.citi.hibernate.model;

import java.util.HashMap;
import java.util.Map;

public class Name {
	
	private String first;
	private String last;
	private Person owner;
	private Map<String,Integer> power = new HashMap<String,Integer>();	
	
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	public Map<String, Integer> getPower() {
		return power;
	}
	public void setPower(Map<String, Integer> power) {
		this.power = power;
	}
	public Name(String string, String string2) {
		this.first=string;
		this.last = string2;
	}
	public Name() {
	}
}
