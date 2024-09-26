package com.dartech.myschola.mapper;

import com.dartech.myschola.dto.RoleDto;
import com.dartech.myschola.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role dtoToEntity(RoleDto dto);

    RoleDto entityToDto(Role entity);

}
