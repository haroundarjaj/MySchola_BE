package com.dartech.myschola.service;

import com.dartech.myschola.dto.AuthResponseDto;
import com.dartech.myschola.dto.LoginDto;

public interface AuthenticationService {

    AuthResponseDto login(LoginDto loginDto);

}
