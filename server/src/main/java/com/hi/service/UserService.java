package com.hi.service;

import com.hi.model.User;

public interface UserService {
	User getUserById(int id);
	
	User getUserByName(String name);
	
	void saveUser(User user);
}
