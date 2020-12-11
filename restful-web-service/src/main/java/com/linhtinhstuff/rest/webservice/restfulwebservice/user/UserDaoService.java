package com.linhtinhstuff.rest.webservice.restfulwebservice.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<>();
	
	private static int count = 3;

	static {
		users.add(new User(1, "Adam", new Date()));
		users.add(new User(2, "Eve", new Date()));
		users.add(new User(3, "Jack", new Date()));
	}

	public List<User> findAll() {
		return users;
	}

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++count);
		}
		users.add(user);
		return user;
	}

	public User findById(int id) {
		return users.stream().filter((user) -> user.getId() == id).findAny().orElse(null);
	}
	
	public User deleteById(int id) {
		Iterator<User> iterator = users.iterator();
		User deletedUser = null;
		while(iterator.hasNext()) {
			deletedUser = iterator.next();
			if (deletedUser.getId() == id) {
				iterator.remove();
				return deletedUser;
			}
		}
		return null;
	}
}
