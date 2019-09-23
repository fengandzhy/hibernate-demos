package org.zhouhy.hibernate.model;

public class Person {
	private int id;
	private String name;
	private IDCard card;
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
	public IDCard getCard() {
		return card;
	}
	public void setCard(IDCard card) {
		this.card = card;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}	
}
