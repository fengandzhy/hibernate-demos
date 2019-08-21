package com.citi.hibernate.model;

public class IDCard {
	private int id;
	private String number;
	private Person person;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	@Override
	public String toString() {
		return "IDCard [id=" + id + ", number=" + number + ", person=" + person + "]";
	}		
}
