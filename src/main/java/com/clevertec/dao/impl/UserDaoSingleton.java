package com.clevertec.dao.impl;

import com.clevertec.dao.UserDao;
import com.clevertec.dbo.HikariCPDataSource;

public class UserDaoSingleton {
	private UserDao userDao;
	private volatile static UserDaoSingleton instance;

	public UserDaoSingleton() {
		try {
			this.userDao = new UserDaoImpl (HikariCPDataSource.getInstance());
		} catch (Exception e) {
			throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
		}
	}

	public static UserDao getInstance() {
		if (instance == null) {
			synchronized (UserDaoSingleton.class) {
				if (instance == null) {
					instance = new UserDaoSingleton();
				}
			}
		}
		return instance.userDao;
	}

}