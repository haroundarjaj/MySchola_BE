package com.dartech.myschola.mapper;

import com.dartech.myschola.dto.RoleDto;
import com.dartech.myschola.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role dtoToEntity(RoleDto dto);

    RoleDto entityToDto(Role entity);

    List<Role> dtoListToEntities(List<RoleDto> dtoList);

    List<RoleDto> entitiesToDtoList(List<Role> roles);
}