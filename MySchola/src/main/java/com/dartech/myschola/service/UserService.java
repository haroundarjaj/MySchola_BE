package com.dartech.myschola.service;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.entity.User;

import java.util.List;

public interface UserService {

    User save(UserDto userDto);
    User update(UserDto userDto);
    void delete(long id);
    List<User> getAll();
    User getById(long id);
    User getByEmail(String email);
}
