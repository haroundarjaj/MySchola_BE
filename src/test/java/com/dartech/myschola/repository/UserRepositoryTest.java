package com.dartech.myschola.repository;

import com.dartech.myschola.entity.Role;
import com.dartech.myschola.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void userRepository_save_returnUser() {
        User user  = User.builder()
                .email("user@gmail.ocm")
                .password("123456789")
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertNotNull(savedUser.getId());
    }

    @Test
    void userRepository_saveUserWithRoles_returnUserWithRoles() {
        User user  = User.builder()
                .email("user@gmail.ocm")
                .password("123456789")
                .build();

        Role role1 = Role.builder()
                .code("USER")
                .name("User")
                .build();
        Role role2 = Role.builder()
                .code("ADMIN")
                .name("Admin")
                .build();

        List<Role> roles =  List.of(role1,role2);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        List<Role> savedUserRoles = savedUser.getRoles();

        Assertions.assertNotNull(savedUserRoles);
        Assertions.assertEquals(2, savedUserRoles.size());
        Assertions.assertEquals(role1.getCode(),savedUserRoles.get(0).getCode());
        Assertions.assertNotNull(savedUserRoles.get(0).getId());
    }

    @Test
    void userRepository_update_returnUser() {
        User user  = User.builder()
                .email("user@gmail.ocm")
                .password("123456789")
                .build();

        User savedUser = userRepository.save(user);
        user.setId(savedUser.getId());
        user.setEmail("updated_user@gmail.ocm");
        User updatedUser = userRepository.save(user);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(savedUser.getId(), updatedUser.getId());
        Assertions.assertEquals("updated_user@gmail.ocm", updatedUser.getEmail());
    }

    @Test
    void userRepository_findAll_returnUsers() {
        User user1  = User.builder()
                .email("user1@gmail.ocm")
                .password("123456789")
                .build();
        User user2  = User.builder()
                .email("user2@gmail.ocm")
                .password("987654321")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        Assertions.assertEquals(2, users.size());
    }

    @Test
    void userRepository_findById_returnUser() {
        User user = User.builder()
                .email("user@gmail.ocm")
                .password("123456789")
                .build();

        User savedUser = userRepository.save(user);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(savedUser.getEmail(), foundUser.getEmail());
    }


    @Test
    void userRepository_findByEmail_returnsUser() {

        User user = User.builder()
                .email("user@gmail.com")
                .password("123456789")
                .build();

        userRepository.save(user);
        User findUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        Assertions.assertNotNull(findUser);
        Assertions.assertEquals(user.getId(), findUser.getId());
    }

    @Test
    void userRepository_delete_returnNull() {
        User user = User.builder()
                .email("user@gmail.com")
                .password("123456789")
                .build();

        User savedUser = userRepository.save(user);

        userRepository.delete(savedUser);

        Assertions.assertNull(userRepository.findById(savedUser.getId()).orElse(null));
    }
}