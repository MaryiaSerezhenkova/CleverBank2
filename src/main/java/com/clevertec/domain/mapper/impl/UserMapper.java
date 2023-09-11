package com.clevertec.domain.mapper.impl;

import com.clevertec.domain.dto.UserDto;
import com.clevertec.domain.entity.User;

public class UserMapper extends BaseMapper<User, UserDto> {
    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected Class<UserDto> getDtoClass() {
        return UserDto.class;
    }
}
