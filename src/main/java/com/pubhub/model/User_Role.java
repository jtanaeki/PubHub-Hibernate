package com.pubhub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity  
@Table(name="user_role", uniqueConstraints=@UniqueConstraint(columnNames={"username"}))
public class User_Role {

	@Id
	@Column(name="username", unique=true, nullable=false)
	private String username;
	@Column(name="role", nullable=true)
	private String role;

	public User_Role(String username, String role) {
		this.username = username;
		this.role = role;
	}
	
	// Default constructor
	public User_Role() {
		this.username = null;
		this.role = null;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "\nUser [username=" + username + ", role=" + role + "]";
	}
	
}
