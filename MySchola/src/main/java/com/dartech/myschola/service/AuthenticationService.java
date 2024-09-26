package com.dartech.myschola.service;

import com.dartech.myschola.dto.LoginDto;
import com.dartech.myschola.dto.UserDto;

public interface AuthenticationService {

    String login(LoginDto loginDto);

}
