package com.citi.hibernate.model;

public class Name2 {
	
	private String nickname;
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public Name2() {
		super();
	}

	public Name2(String nickname) {
		super();
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj != null&&obj.getClass() == Name2.class){
			Name2 target = (Name2)obj;
			if (target.getNickname().equals(getNickname())){
				return true;
			}
		}
		return false;
	}
	
	public int hashCode(){
		return getNickname().hashCode() * 13;
	}
}
