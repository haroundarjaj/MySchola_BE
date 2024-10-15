package com.dartech.myschola.repository;

import com.dartech.myschola.entity.Permission;
import com.dartech.myschola.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void roleRepository_save_returnRole() {
        Role role = Role.builder()
                .code("USER")
                .name("User")
                .build();

        Role savedRole = roleRepository.save(role);

        Assertions.assertNotNull(savedRole);
        Assertions.assertNotNull(roleRepository.findById(savedRole.getId()));
    }

    @Test
    void roleRepository_saveRoleWithPermissions_returnRoleWithPermissions() {
        Role role = Role.builder()
                .code("USER")
                .name("User")
                .build();

        Permission permission1 = Permission.builder()
                .action("Create")
                .subject("User")
                .build();
        Permission permission2 = Permission.builder()
                .action("Update")
                .subject("User")
                .build();

        List<Permission> permissions =  List.of(permission1,permission2);
        role.setPermissions(permissions);

        Role savedRole = roleRepository.save(role);
        List<Permission> savedRolePermissions = savedRole.getPermissions();

        Assertions.assertNotNull(savedRolePermissions);
        Assertions.assertEquals(2, savedRolePermissions.size());
        Assertions.assertEquals(permission1.getAction(),savedRolePermissions.get(0).getAction());
        Assertions.assertNotNull(savedRolePermissions.get(0).getId());
    }

    @Test
    void roleRepository_update_returnRole() {
        Role role = Role.builder()
                .code("USER")
                .name("User")
                .build();

        Role savedRole = roleRepository.save(role);
        role.setId(savedRole.getId());
        role.setCode("ADMIN");
        Role updatedRole = roleRepository.save(role);

        Assertions.assertNotNull(updatedRole);
        Assertions.assertEquals(savedRole.getId(), updatedRole.getId());
        Assertions.assertEquals("ADMIN", updatedRole.getCode());
    }

    @Test
    void roleRepository_findAll_returnRoles() {
        Role role1 = Role.builder()
                .code("USER")
                .name("User")
                .build();
        Role role2 = Role.builder()
                .code("ADMIN")
                .name("Admin")
                .build();

        roleRepository.save(role1);
        roleRepository.save(role2);

        List<Role> roles = roleRepository.findAll();

        Assertions.assertNotNull(roles);
        Assertions.assertEquals(2, roles.size());
    }

    @Test
    void roleRepository_findById_returnRole() {
        Role role = Role.builder()
                .code("USER")
                .name("User")
                .build();

        Role savedRole = roleRepository.save(role);
        Role foundRole = roleRepository.findById(savedRole.getId()).orElse(null);

        Assertions.assertNotNull(foundRole);
        Assertions.assertEquals(savedRole.getCode(), foundRole.getCode());
    }

    @Test
    void roleRepository_findByCode_returnRole() {
        Role role = Role.builder()
                .code("USER")
                .name("User")
                .build();

        Role savedRole = roleRepository.save(role);
        Role foundRole = roleRepository.findByCode(savedRole.getCode()).orElse(null);

        Assertions.assertNotNull(foundRole);
        Assertions.assertEquals("USER", foundRole.getCode());
    }

    @Test
    void roleRepository_delete_returnRole() {
        Role role = Role.builder()
                .code("USER")
                .name("User")
                .build();

        Role savedRole  = roleRepository.save(role);
        roleRepository.delete(savedRole);

        Assertions.assertNull(roleRepository.findById(savedRole.getId()).orElse(null));
    }

}