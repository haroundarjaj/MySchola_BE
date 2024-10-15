package com.dartech.myschola.service;

import com.dartech.myschola.dto.RoleDto;
import com.dartech.myschola.entity.Role;
import com.dartech.myschola.mapper.RoleMapper;
import com.dartech.myschola.repository.RoleRepository;
import com.dartech.myschola.utils.OperationLogGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleMapper roleMapper;
    @Mock
    private OperationLogGenerator<Role> operationLogGenerator;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private RoleDto roleDto;

    @BeforeEach
    void setUp() {
        roleDto = RoleDto.builder()
                .name("admin")
                .code("ADMIN")
                .build();
        role = Role.builder()
                .name("admin")
                .code("ADMIN")
                .build();
    }


    @Test
    void roleService_save_returnRole() {

        when(roleMapper.dtoToEntity(roleDto)).thenReturn(role);
        when(operationLogGenerator.generateCreationLog(role)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);

        Role result = roleService.save(roleDto);

        assertNotNull(result);

        verify(roleMapper).dtoToEntity(roleDto);
        verify(operationLogGenerator).generateCreationLog(role);
        verify(roleRepository).save(role);
    }

    @Test
    void roleService_update_returnRole() {
        roleDto.setId(1L);
        Role oldRole = Role.builder()
                .id(1L)
                .createdAt(new Date().toString())
                .createdBy("admin")
                .build();

        when(roleMapper.dtoToEntity(roleDto)).thenReturn(role);
        when(roleRepository.findById(roleDto.getId())).thenReturn(Optional.of(oldRole));
        when(operationLogGenerator.generateUpdateLog(role)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);

        Role result = roleService.update(roleDto);

        assertNotNull(result);
        assertEquals(oldRole.getCreatedBy(), result.getCreatedBy());
        assertEquals(oldRole.getCreatedAt(), result.getCreatedAt());

        verify(roleMapper).dtoToEntity(roleDto);
        verify(roleRepository).findById(roleDto.getId());
        verify(operationLogGenerator).generateUpdateLog(role);
        verify(roleRepository).save(result);
    }

    @Test
    void roleService_getAll_returnAllRoles() {
        Role role1 = Role.builder()
                .name("admin")
                .code("ADMIN")
                .build();

        Role role2 = Role.builder()
                .name("user")
                .code("User")
                .build();
        List<Role> roles = List.of(role1, role2);

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> result = roleService.getAll();

        assertNotNull(result);
        assertEquals(roles.size(), result.size());
        assertEquals(role1.getName(), result.get(0).getName());
        assertEquals(role2.getName(), result.get(1).getName());

        verify(roleRepository).findAll();
    }

    @Test
    void roleService_getById_returnRole() {
        long id = 1L;
        role.setId(id);

        when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        Role result = roleService.getById(id);

        assertNotNull(result);
        assertEquals(role.getName(), result.getName());

        verify(roleRepository).findById(id);
    }

    @Test
    void roleService_getByCode_returnRole() {
        String code = "ADMIN";

        when(roleRepository.findByCode(code)).thenReturn(Optional.of(role));

        Role result = roleService.getByCode(code);

        assertNotNull(result);
        assertEquals(role.getName(), result.getName());

        verify(roleRepository).findByCode(code);
    }

    @Test
    void roleService_delete_roleFoundAndDeleted() {
        long id = 1L;
        role.setId(id);

        when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        roleService.delete(id);

        verify(roleRepository).findById(id);
    }
}