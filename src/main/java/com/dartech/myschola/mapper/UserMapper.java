package com.dartech.myschola.mapper;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.dto.UserResponseDto;
import com.dartech.myschola.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(UserDto dto);
    @Mapping(target = "createdAt", source = "createdAt")
    UserResponseDto entityToResponseDto(User user);

}
