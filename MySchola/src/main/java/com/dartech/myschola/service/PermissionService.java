package com.dartech.myschola.service;

import com.dartech.myschola.dto.PermissionDto;
import com.dartech.myschola.entity.Permission;

import java.util.List;

public interface PermissionService {

    Permission save(PermissionDto permissionDto);
    Permission update(PermissionDto permissionDto);
    void delete(long id);
    List<Permission> getAll();
    Permission getById(long id);

}
