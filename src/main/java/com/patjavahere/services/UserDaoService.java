package com.patjavahere.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

import com.patjavahere.model.User;

@Component
public class UserDaoService {
	public static int usersCount = 5;
	private static List<User> users = new ArrayList<>();
	static {
		users.add(new User(1, "n", new Date()));
		users.add(new User(2, "R", new Date()));
		users.add(new User(3, "am", new Date()));
		users.add(new User(4, "An", new Date()));
		users.add(new User(5, "ck", new Date()));
	}

	public List<User> findAll() {
		return UserDaoService.users;
	}

	public User save(final User user) {
		if (user.getId() == null) {
			user.setId(++usersCount);
		}
		UserDaoService.users.add(user);
		return user;
	}

	public User findOne(final int id) {
		for (final User user : UserDaoService.users) {
			if (user.getId() == id)
				return user;
		}
		return null;
	}

	public User deleteById(final int id) {
		final Iterator<User> iterator = UserDaoService.users.iterator();
		while (iterator.hasNext()) {
			final User user = iterator.next();
			if (user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}
}