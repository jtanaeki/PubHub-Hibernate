package com.pubhub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity  
@Table(name="user", uniqueConstraints=@UniqueConstraint(columnNames={"username"}))
public class User {

	@Id
    @Column(name="username", unique=true, nullable=false)
	private String username;
	@Column(name="password", nullable=true)
	private String password;
	@Column(name="full_name", nullable=true)
	private String fullName;

	public User(String username, String password, String fullName) {
		this.username = username;
		this.password = password;
		this.fullName = fullName;
	}
	
	// Default constructor
	public User() {
		this.username = null;
		this.password = null;
		this.fullName = null;
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
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "\nUser [username=" + username + ", password=" + password +  ", fullName=" + fullName + "]";
	}
	
}
