package com.dartech.myschola.service;

import com.dartech.myschola.dto.LoginDto;

public interface AuthenticationService {

    String login(LoginDto loginDto);

}
