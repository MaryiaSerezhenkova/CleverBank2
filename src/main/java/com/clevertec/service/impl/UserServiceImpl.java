package com.clevertec.service.impl;

import com.clevertec.dao.UserDao;
import com.clevertec.domain.dto.UserDto;
import com.clevertec.domain.entity.User;
import com.clevertec.domain.mapper.impl.UserMapper;
import com.clevertec.service.UserService;
import java.time.LocalDateTime;

public class UserServiceImpl implements UserService {

private final UserDao userDao;
private UserMapper userMapper;

    public UserServiceImpl(UserDao userDao, UserMapper userMapper) {
        this.userDao=userDao;
    }

    @Override
    public UserDto create(UserDto item) {
        User user = new User();
        user.setName(item.getName());
        
        user.setDtCreate(LocalDateTime.now());
        user.setDtUpdate(user.getDtCreate());
    
        return userMapper.toDTO(userDao.create(user));
    }


    @Override
    public UserDto read(long id) {
        return userMapper.toDTO(userDao.get(id));
    }

    @Override
    public UserDto update(long id, LocalDateTime dtUpdate, UserDto user) {
        User readed = userDao.get(id);

        if (readed == null) {
            throw new IllegalArgumentException("Позиция не найдена");
        }

        if (!readed.getDtUpdate().isEqual(dtUpdate)) {
            throw new IllegalArgumentException("К сожалению позиция уже была отредактирована кем-то другим");
        }

        readed.setDtUpdate(LocalDateTime.now());
        readed.setName(user.getName());
        return userMapper.toDTO(userDao.update(id, dtUpdate, readed));
    }


    @Override
    public void delete(long id, LocalDateTime dtUpdate) {
        User readed = userDao.get(id);
        if (readed == null) {
            throw new IllegalArgumentException("Позиция не найдена");
        }

        if (!readed.getDtUpdate().isEqual(dtUpdate)) {
            throw new IllegalArgumentException("К сожалению позиция уже была отредактирована кем-то другим");
        }

        userDao.delete(id, dtUpdate);


    }
}
