package com.dartech.myschola.mapper;

import com.dartech.myschola.dto.PermissionDto;
import com.dartech.myschola.entity.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission dtoToEntity(PermissionDto dto);

    PermissionDto entityToDto(Permission entity);

    List<Permission> dtoListToEntities(List<PermissionDto> dtoList);

    List<PermissionDto> entitiesToDtoList(List<Permission> permissions);

}
