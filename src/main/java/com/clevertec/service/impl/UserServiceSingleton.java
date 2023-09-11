package com.clevertec.service.impl;

import com.clevertec.dao.impl.UserDaoSingleton;
import com.clevertec.domain.mapper.impl.UserMapper;
import com.clevertec.service.UserService;

public class UserServiceSingleton {
	 private final UserService userService;
	    private volatile static UserServiceSingleton firstInstance = null;

	    public UserServiceSingleton() {
	        this.userService = new UserServiceImpl(UserDaoSingleton.getInstance(), new UserMapper());
	    }

	    public static UserService getInstance() {
	        synchronized (UserServiceSingleton.class) {
	            if (firstInstance == null) {
	                firstInstance = new UserServiceSingleton();
	            }
	        }
	        return firstInstance.userService;
	    }
	}