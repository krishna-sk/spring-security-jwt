package com.jwt.service;

import java.util.List;

import com.jwt.entity.User;

public interface IUserService {

	User saveUser(User user);

	User getUserByUsername(String username);

	boolean userExistsByUsername(String username);

	List<User> getAllUsers();

}
