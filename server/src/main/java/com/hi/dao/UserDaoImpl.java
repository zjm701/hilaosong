package com.hi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hi.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao {

	@SuppressWarnings("unchecked")
	public User getUserById(int id) {
		List<User> users = getSession().createQuery("from User where id = ?")
				.setParameter(0, id).list();
		if(users.isEmpty()){
			return null;
		}
		return users.get(0);
	}

	@SuppressWarnings("unchecked")
	public User getUserByName(String name) {
		List<User> users = getSession().createQuery("from User where name = ?")
				.setParameter(0, name).list();
		if(users.isEmpty()){
			return null;
		}
		return users.get(0);
	}

	public void saveUser(User user) {
		getSession().save(user);
	}

}
