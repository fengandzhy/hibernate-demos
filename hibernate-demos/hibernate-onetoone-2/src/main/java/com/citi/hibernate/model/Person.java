package com.citi.hibernate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Person")
@Table(name = "t_person")
public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4409157469169126378L;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	
//	@OneToOne
//	@JoinColumn(name = "card_id")
	@ManyToOne
    @JoinColumn(name="card_id",unique=true)
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
