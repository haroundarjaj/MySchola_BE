package com.dartech.myschola.service;

import com.dartech.myschola.dto.RoleDto;
import com.dartech.myschola.entity.Role;
import com.dartech.myschola.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
@WithMockUser(username = "admin@gmail.com")
public class RoleServiceIntegrationTest {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;

    RoleDto roleDto;

    @BeforeEach
    void setUp() {
        roleDto = RoleDto.builder()
                .name("Admin")
                .code("ADMIN")
                .build();
    }

    @Test
    void roleService_save_returnRole() {
        Role savedRole = roleService.save(roleDto);

        assertNotNull(savedRole);
        assertNotNull(savedRole.getId());
        assertEquals(roleDto.getName(), savedRole.getName());
        assertEquals(roleDto.getCode(), savedRole.getCode());

        Role foundRole = roleRepository.findById(savedRole.getId()).orElse(null);

        assertNotNull(foundRole);
        assertEquals(roleDto.getName(), foundRole.getName());
    }
}
