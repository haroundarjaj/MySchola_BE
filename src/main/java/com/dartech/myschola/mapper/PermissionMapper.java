package com.dartech.myschola.mapper;

import com.dartech.myschola.dto.PermissionDto;
import com.dartech.myschola.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission dtoToEntity(PermissionDto dto);

}
