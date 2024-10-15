package com.dartech.myschola.repository;

import com.dartech.myschola.entity.Permission;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    void permissionRepository_save_returnPermission() {
        Permission permission = Permission.builder()
                .action("Create")
                .subject("User")
                .build();

        Permission savedPermission = permissionRepository.save(permission);

        Assertions.assertNotNull(savedPermission);
        Assertions.assertNotNull(permissionRepository.findById(savedPermission.getId()));
    }

    @Test
    void permissionRepository_update_returnPermission() {
        Permission permission = Permission.builder()
                .action("Create")
                .subject("User")
                .build();

        Permission savedPermission = permissionRepository.save(permission);
        permission.setId(savedPermission.getId());
        permission.setAction("Update");
        Permission updatedPermission = permissionRepository.save(permission);

        Assertions.assertNotNull(updatedPermission);
        Assertions.assertEquals(savedPermission.getId(), updatedPermission.getId());
        Assertions.assertEquals("Update", updatedPermission.getAction());
    }

    @Test
    void permissionRepository_findAll_returnPermissions() {
        Permission permission1 = Permission.builder()
                .action("Create")
                .subject("User")
                .build();
        Permission permission2 = Permission.builder()
                .action("Update")
                .subject("User")
                .build();

        permissionRepository.save(permission1);
        permissionRepository.save(permission2);

        List<Permission> permissions = permissionRepository.findAll();

        Assertions.assertNotNull(permissions);
        Assertions.assertEquals(2, permissions.size());
    }

    @Test
    void permissionRepository_findById_returnPermission() {
        Permission permission = Permission.builder()
                .action("Create")
                .subject("User")
                .build();

        Permission savedPermission = permissionRepository.save(permission);
        Permission foundPermission = permissionRepository.findById(savedPermission.getId()).orElse(null);

        Assertions.assertNotNull(foundPermission);
        Assertions.assertEquals(savedPermission.getAction(), foundPermission.getAction());
    }

    @Test
    void permissionRepository_delete_returnPermission() {
        Permission permission = Permission.builder()
                .action("Create")
                .subject("User")
                .build();

        Permission savedPermission  = permissionRepository.save(permission);
        permissionRepository.delete(savedPermission);

        Assertions.assertNull(permissionRepository.findById(savedPermission.getId()).orElse(null));
    }

}