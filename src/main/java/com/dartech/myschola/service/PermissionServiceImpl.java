package com.dartech.myschola.service;

import com.dartech.myschola.dto.PermissionDto;
import com.dartech.myschola.entity.Permission;
import com.dartech.myschola.mapper.PermissionMapper;
import com.dartech.myschola.repository.PermissionRepository;
import com.dartech.myschola.utils.OperationLogGenerator;
import com.dartech.myschola.utils.exception.PredefinedExceptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final OperationLogGenerator<Permission> operationLogGenerator;

    PermissionServiceImpl(
            PermissionRepository permissionRepository,
            PermissionMapper permissionMapper,
            OperationLogGenerator<Permission> operationLogGenerator
    ) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
        this.operationLogGenerator = operationLogGenerator;
    }

    @Override
    public Permission save(PermissionDto permissionDto) {
        Permission permission = permissionMapper.dtoToEntity(permissionDto);
        permission = operationLogGenerator.generateCreationLog(permission);
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(PermissionDto permissionDto) {
        Permission permission = permissionMapper.dtoToEntity(permissionDto);
        Permission oldPermission = permissionRepository
                .findById(permission.getId())
                .orElseThrow(() -> PredefinedExceptions.notFoundException);
        permission = operationLogGenerator.generateUpdateLog(permission);
        permission.setCreatedAt(oldPermission.getCreatedAt());
        permission.setCreatedBy(oldPermission.getCreatedBy());
        return permissionRepository.save(permission);
    }

    @Override
    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission getById(long id) {
        return permissionRepository.findById(id).orElseThrow(() -> PredefinedExceptions.notFoundException);
    }

    @Override
    public void delete(long id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        if (permission.isEmpty()) {
            throw PredefinedExceptions.notFoundException;
        }
        permission.ifPresent(permissionRepository::delete);
    }
}
