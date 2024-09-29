package com.dartech.myschola.mapper;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(UserDto dto);

}
