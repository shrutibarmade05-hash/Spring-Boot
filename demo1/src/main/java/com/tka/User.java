package com.tka;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

	@Id
	private long id;
	private String Fname;
	private String Lname;
	private String email;
	private String userId;
	private String city;
	
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", Fname=" + Fname + ", Lname=" + Lname + ", email=" + email + ", userId=" + userId
				+ ", city=" + city + "]";
	}
	
	public User(long id, String fname, String lname, String email, String userId, String city) {
		super();
		this.id = id;
		Fname = fname;
		Lname = lname;
		this.email = email;
		this.userId = userId;
		this.city = city;
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
