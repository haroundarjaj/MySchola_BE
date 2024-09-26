package com.dartech.myschola.utils;

import com.dartech.myschola.entity.Permission;
import com.dartech.myschola.entity.Role;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.repository.PermissionRepository;
import com.dartech.myschola.repository.RoleRepository;
import com.dartech.myschola.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DataGenerator {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DataGenerator(
            PermissionRepository permissionRepository,
            RoleRepository roleRepository,
            UserRepository userRepository
    ) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void generatePermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        if(permissions.isEmpty()) {
            permissions = new ArrayList<>();
            permissions.add(new Permission(null, "ACCESS", "USER"));
            permissions.add(new Permission(null, "ADD", "USER"));
            permissions.add(new Permission(null, "EDIT", "USER"));
            permissions.add(new Permission(null, "DELETE", "USER"));
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            permissions.forEach(permission -> {
                permission.setCreatedBy("System");
                permission.setCreatedAt(timeStamp);
            });
            permissionRepository.saveAll(permissions);
        }
    }

    @Transactional
    public void generateRoles() {
        Optional<Role> role = roleRepository.findByCode("SUPER_ADMIN");
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        if(role.isEmpty()) {
            Role role1 = new Role();
            role1.setName("Super admin");
            role1.setCode("SUPER_ADMIN");
            role1.setDescription("Super admin role granted all the privileges in the app");
            role1.setCreatedBy("System");
            role1.setCreatedAt(timeStamp);
            List<Permission> permissions = permissionRepository.findAll();
            role1.setPermissions(permissions);
            roleRepository.save(role1);
        }
        role = roleRepository.findByCode("USER");
        if(role.isEmpty()){
            Role role2 = new Role();
            role2.setName("User");
            role2.setCode("US");
            role2.setDescription("Normal user that has access to the basic options in the app");
            role2.setCreatedBy("System");
            role2.setCreatedAt(timeStamp);
            roleRepository.save(role2);
        }
    }

    @Transactional
    public void generateSuperAdmin() {
        Optional<User> user = userRepository.findByEmail("super@admin.com");
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        if (user.isEmpty()){
            User newUser = User.builder()
                    .firstName("Super")
                    .lastName("Admin")
                    .email("super@admin.com")
                    .password("$2a$10$fVQAoBypWWp103pHLKneQOfUVEl/dz5lgEzQy0Lcx83dA7v4AQnAK")
                    .isActive(true)
                    .createdAt(timeStamp)
                    .createdBy("System")
                    .build();
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByCode("SUPER_ADMIN").orElse(null));
            newUser.setRoles(roles);
            userRepository.save(newUser);
        }
    }
}
