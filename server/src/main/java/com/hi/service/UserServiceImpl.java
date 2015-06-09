package com.hi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.UserDao;
import com.hi.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;

	
	public User getUserById(int id) {
		return dao.getUserById(id);
	}

	public User getUserByName(String name) {
		return dao.getUserByName(name);
	}

	public void saveUser(User user) {
		dao.saveUser(user);
	}

}
