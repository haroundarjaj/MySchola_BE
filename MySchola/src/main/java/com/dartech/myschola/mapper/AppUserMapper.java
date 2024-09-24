package com.dartech.myschola.mapper;

import com.dartech.myschola.dto.AppUserDto;
import com.dartech.myschola.entity.AppUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUser dtoToEntity(AppUserDto dto);

    AppUserDto entityToDto(AppUser user);
    
    List<AppUser> dtoListToEntities(List<AppUserDto> dtos);

    List<AppUserDto> entitiesToDtoList(List<AppUser> users);

}
