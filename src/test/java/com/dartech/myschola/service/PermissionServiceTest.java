package com.dartech.myschola.service;

import com.dartech.myschola.dto.PermissionDto;
import com.dartech.myschola.entity.Permission;
import com.dartech.myschola.mapper.PermissionMapper;
import com.dartech.myschola.repository.PermissionRepository;
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
class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;
    @Mock
    private PermissionMapper permissionMapper;
    @Mock
    private OperationLogGenerator<Permission> operationLogGenerator;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    private Permission permission;
    private PermissionDto permissionDto;

    @BeforeEach
    void setUp() {
        permissionDto = PermissionDto.builder()
                .action("CREATE")
                .subject("USER")
                .build();
        permission = Permission.builder()
                .action("CREATE")
                .subject("USER")
                .build();
    }

    @Test
    void permissionService_save_returnPermission() {
        when(permissionMapper.dtoToEntity(permissionDto)).thenReturn(permission);
        when(operationLogGenerator.generateCreationLog(permission)).thenReturn(permission);
        when(permissionRepository.save(permission)).thenReturn(permission);

        Permission result = permissionService.save(permissionDto);

        assertNotNull(result);
        assertEquals(permission.getAction(), result.getAction());

        verify(permissionMapper).dtoToEntity(permissionDto);
        verify(operationLogGenerator).generateCreationLog(permission);
        verify(permissionRepository).save(permission);
    }

    @Test
    void permissionService_update_returnPermission() {
        permissionDto.setId(1L);
        Permission oldPermission = Permission.builder()
                .id(1L)
                .createdAt(new Date().toString())
                .createdBy("admin")
                .build();

        when(permissionMapper.dtoToEntity(permissionDto)).thenReturn(permission);
        when(permissionRepository.findById(permissionDto.getId())).thenReturn(Optional.of(oldPermission));
        when(operationLogGenerator.generateUpdateLog(permission)).thenReturn(permission);
        when(permissionRepository.save(permission)).thenReturn(permission);

        Permission result = permissionService.update(permissionDto);

        assertNotNull(result);
        assertEquals(oldPermission.getCreatedAt(), permission.getCreatedAt());
        assertEquals(oldPermission.getCreatedBy(), permission.getCreatedBy());

        verify(permissionMapper).dtoToEntity(permissionDto);
        verify(permissionRepository).findById(permissionDto.getId());
        verify(operationLogGenerator).generateUpdateLog(permission);
        verify(permissionRepository).save(permission);
    }

    @Test
    void permissionService_getAll_returnAllPermissions() {
        Permission permission1 = Permission.builder()
                .action("CREATE")
                .subject("USER")
                .build();

        Permission permission2 = Permission.builder()
                .action("UPDATE")
                .subject("USER")
                .build();

        List<Permission> permissions = List.of(permission1, permission2);
        when(permissionRepository.findAll()).thenReturn(permissions);

        List<Permission> result = permissionService.getAll();

        assertNotNull(result);
        assertEquals(permissions.size(), result.size());
        assertEquals(permission1.getAction(), result.get(0).getAction());
        assertEquals(permission2.getAction(), result.get(1).getAction());

        verify(permissionRepository).findAll();
    }

    @Test
    void permissionService_getById_returnPermission() {
        long id = 1L;
        permission.setId(id);

        when(permissionRepository.findById(id)).thenReturn(Optional.of(permission));

        Permission result = permissionService.getById(id);

        assertNotNull(result);
        assertEquals(permission.getId(), result.getId());

        verify(permissionRepository).findById(id);
    }

    @Test
    void permissionService_delete_permissionFoundAndDeleted() {
        long id = 1L;
        permission.setId(id);

        when(permissionRepository.findById(id)).thenReturn(Optional.of(permission));

        permissionService.delete(id);

        verify(permissionRepository).findById(id);
        verify(permissionRepository).delete(permission);
    }
}