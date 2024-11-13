package com.dartech.myschola.service;

import com.dartech.myschola.dto.ResetPasswordRequestDto;
import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.dto.UserResponseDto;
import com.dartech.myschola.entity.PasswordResetToken;
import com.dartech.myschola.entity.User;

import java.util.List;

public interface UserService {

    User save(UserDto userDto);
    User update(UserDto userDto);
    List<UserResponseDto> getAll();
    User getById(long id);
    User getByEmail(String email);
    void approveUser(long id);
    void forgotPassword(String email);
    void resetPassword(ResetPasswordRequestDto request);
    void delete(long id);
    PasswordResetToken getTokenInfo(String token);
}
