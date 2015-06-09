package com.hi.dao;

import com.hi.model.User;

public interface UserDao {
	User getUserById(int id);
	
	User getUserByName(String name);
	
	void saveUser(User user);
}
