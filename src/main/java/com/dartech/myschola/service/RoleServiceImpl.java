package com.dartech.myschola.service;

import com.dartech.myschola.dto.RoleDto;
import com.dartech.myschola.entity.Role;
import com.dartech.myschola.mapper.RoleMapper;
import com.dartech.myschola.repository.RoleRepository;
import com.dartech.myschola.utils.OperationLogGenerator;
import com.dartech.myschola.utils.exception.PredefinedExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final OperationLogGenerator<Role> operationLogGenerator;

    @Autowired
    RoleServiceImpl(
            RoleRepository roleRepository,
            RoleMapper roleMapper,
            OperationLogGenerator<Role> operationLogGenerator
    ) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.operationLogGenerator = operationLogGenerator;
    }

    @Override
    public Role save(RoleDto roleDto) {
        Role role = roleMapper.dtoToEntity(roleDto);
        role = operationLogGenerator.generateCreationLog(role);
        return roleRepository.save(role);
    }

    @Override
    public Role update(RoleDto roleDto) {
        Role role = roleMapper.dtoToEntity(roleDto);
        Role oldRole = roleRepository.findById(roleDto.getId()).orElseThrow(() -> PredefinedExceptions.notFoundException);
        role = operationLogGenerator.generateCreationLog(role);
        role.setCreatedAt(oldRole.getCreatedAt());
        role.setCreatedBy(oldRole.getCreatedBy());
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(long id) {
        return roleRepository.findById(id).orElseThrow(() -> PredefinedExceptions.notFoundException);
    }

    @Override
    public Role getByCode(String name) {

        return roleRepository.findByCode(name).orElseThrow(() -> PredefinedExceptions.notFoundException);
    }

    @Override
    public void delete(long id) {
        Optional<Role> role = roleRepository.findById(id);
        if(role.isEmpty()) {
            throw PredefinedExceptions.notFoundException;
        }
        role.ifPresent(roleRepository::delete);
    }

}
