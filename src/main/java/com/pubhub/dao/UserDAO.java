package com.pubhub.dao;

import java.util.List;

import com.pubhub.model.User;

/**
 * Interface for our Data Access Object to handle database queries related to User.
 */
public interface UserDAO {

	public List<User> getAllUsers();
	
	public boolean authenticateUser(String user, String pass);
	public boolean addUser(String user, String pass, String name);
	public boolean changePass(String user, String pass);

}
