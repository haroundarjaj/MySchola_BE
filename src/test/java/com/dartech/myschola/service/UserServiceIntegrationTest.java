package com.dartech.myschola.service;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.repository.UserRepository;
import com.dartech.myschola.utils.exception.CustomException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
@WithMockUser(username = "admin@gmail.com")
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .email("user@gmail.com")
                .password("123456789")
                .build();
    }

    @Test
    void userService_save_returnUser() {
        User savedUser = userService.save(userDto);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(userDto.getEmail(), savedUser.getEmail());
        assertTrue(passwordEncoder.matches(userDto.getPassword(), savedUser.getPassword()));
        assertEquals("admin@gmail.com", savedUser.getCreatedBy());

        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals(userDto.getEmail(), foundUser.getEmail());
    }

    @Test
    void userService_update_returnUser() {
        User savedUser = userService.save(userDto);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(userDto.getEmail(), savedUser.getEmail());

        long id = savedUser.getId();

        assertEquals(userDto.getEmail(), savedUser.getEmail());
        assertTrue(passwordEncoder.matches(userDto.getPassword(), savedUser.getPassword()));

        UserDto userDto1 = UserDto.builder()
                .id(id)
                .email("user2@gmail.com")
                .password("987654321")
                .build();
        User savedUser2 = userService.update(userDto1);
        User foundUser = userRepository.findById(savedUser2.getId()).orElse(null);

        assertNotNull(foundUser);
        System.out.println(savedUser.getEmail());
        System.out.println(foundUser.getEmail());
        assertEquals(savedUser.getId(), foundUser.getId());
        assertNotEquals(userDto.getEmail(), foundUser.getEmail());
        assertEquals("admin@gmail.com", foundUser.getCreatedBy());
        assertEquals("admin@gmail.com", foundUser.getLastUpdatedBy());
        assertFalse(passwordEncoder.matches(savedUser.getPassword(), foundUser.getPassword()));
    }

    @Test
    void userService_getAll_returnAllUsers() {
        UserDto userDto2 = UserDto.builder()
                .email("user2@gmail.com")
                .password("987654321")
                .build();
        userService.save(userDto);
        userService.save(userDto2);

        List<User> users = userService.getAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(userDto.getEmail(), users.get(0).getEmail());
        assertTrue(passwordEncoder.matches(userDto.getPassword(), users.get(0).getPassword()));
        assertEquals(userDto2.getEmail(), users.get(1).getEmail());
        assertTrue(passwordEncoder.matches(userDto2.getPassword(), users.get(1).getPassword()));
    }

    @Test
    void userService_getById_returnUser() {
        User savedUser = userService.save(userDto);

        assertNotNull(savedUser);

        User foundUser = userService.getById(savedUser.getId());

        assertNotNull(foundUser);
        assertEquals(userDto.getEmail(), foundUser.getEmail());
        assertTrue(passwordEncoder.matches(userDto.getPassword(), foundUser.getPassword()));
    }

    @Test
    void userService_getByEmail_returnUser() {
        User savedUser = userService.save(userDto);

        assertNotNull(savedUser);

        User foundUser = userService.getByEmail(userDto.getEmail());

        assertNotNull(foundUser);
        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals(userDto.getEmail(), foundUser.getEmail());
        assertTrue(passwordEncoder.matches(userDto.getPassword(), foundUser.getPassword()));
    }

    @Test
    void userService_delete_userFoundAndDeleted() {
        User savedUser = userService.save(userDto);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());

        userService.delete(savedUser.getId());

        assertThrows(CustomException.class, ()-> userService.getById(savedUser.getId()));
    }
}
