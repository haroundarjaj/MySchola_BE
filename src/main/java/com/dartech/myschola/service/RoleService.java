package com.dartech.myschola.service;

import com.dartech.myschola.dto.RoleDto;
import com.dartech.myschola.entity.Role;

import java.util.List;


public interface RoleService {

    Role save(RoleDto roleDto);
     Role update(RoleDto roleDto);
     List<Role> getAll();
     Role getById(long id);
     Role getByCode(String name);
     void delete(long id);

}
