package com.clevertec.dao;

import com.clevertec.domain.entity.User;

import java.time.LocalDateTime;

public interface UserDao {

    User create (User user);
    User update (long id, LocalDateTime dtUpdate, User user);
    User get (long id);
    void delete(long id, LocalDateTime dtUpdate);


}
