package com.dartech.myschola.mapper;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.dto.LoginDto;
import com.dartech.myschola.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(UserDto dto);

    UserDto entityToDto(User user);
    
    List<User> dtoListToEntities(List<UserDto> dtoList);

    List<UserDto> entitiesToDtoList(List<User> users);

    LoginDto appUserToLoginDto(User user);

}
