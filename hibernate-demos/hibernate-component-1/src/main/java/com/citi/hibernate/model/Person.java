package com.citi.hibernate.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Person {
	private int id;
	private int age;
	//组件的属性为集合，Name类中有Map<String,Integer> power，power属性为Map集合
	private Name name;
	//集合属性元素为组件
	private List<Name2> nicks = new ArrayList<Name2>();
	//组件作为Map的索引
	private Map<Name2,Integer> nickPower = new HashMap<Name2,Integer>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public List<Name2> getNicks() {
		return nicks;
	}
	public void setNicks(List<Name2> nicks) {
		this.nicks = nicks;
	}
	public Map<Name2, Integer> getNickPower() {
		return nickPower;
	}
	public void setNickPower(Map<Name2, Integer> nickPower) {
		this.nickPower = nickPower;
	}
}
