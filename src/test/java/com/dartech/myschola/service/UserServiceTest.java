package com.dartech.myschola.service;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.mapper.UserMapper;
import com.dartech.myschola.repository.UserRepository;
import com.dartech.myschola.utils.OperationLogGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private OperationLogGenerator<User> operationLogGenerator;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .email("user@gmail.ocm")
                .password("123456789")
                .build();
        user = User.builder()
                .email("user@gmail.com")
                .password("123456789")
                .build();
    }

    @Test
    void userService_save_returnUser() {
        String encodedPassword = "passwordEncoded";
        User userWithEncodedPassword = User.builder()
                .email("user@gmail.com")
                .password("passwordEncoded")
                .build();
        when(userMapper.dtoToEntity(userDto)).thenReturn(user);
        when(operationLogGenerator.generateCreationLog(user)).thenReturn(user);
        when(passwordEncoder.encode("123456789")).thenReturn(encodedPassword);
        when(userRepository.save(userWithEncodedPassword)).thenReturn(user);

        User result = userService.save(userDto);

        assertNotNull(result);
        assertEquals(encodedPassword, userWithEncodedPassword.getPassword());
        assertFalse(result.isActive());

        verify(userMapper).dtoToEntity(userDto);
        verify(operationLogGenerator).generateCreationLog(user);
        verify(passwordEncoder).encode("123456789");
        verify(userRepository).save(user);
    }

    @Test
    void userService_update_returnUser() {
        userDto.setId(1L);
        User userOld = User.builder()
                .id(1L)
                .createdAt(new Date().toString())
                .createdBy("admin")
                .build();

        when(userMapper.dtoToEntity(userDto)).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(userOld));
        when(operationLogGenerator.generateUpdateLog(user)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.update(userDto);

        assertNotNull(result);
        assertEquals(userOld.getCreatedAt(), user.getCreatedAt());
        assertEquals(userOld.getCreatedBy(), user.getCreatedBy());

        verify(userMapper).dtoToEntity(userDto);
        verify(userRepository).findById(user.getId());
        verify(operationLogGenerator).generateUpdateLog(user);
        verify(userRepository).save(user);
    }

    @Test
    void useService_getAll_returnUsersList() {
        User user1 = User.builder()
                .email("user1@gmail.com")
                .password("123456789")
                .build();
        User user2 = User.builder()
                .email("user2@gmail.com")
                .password("987654321")
                .build();
        List<User> users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAll();

        assertNotNull(result);
        assertEquals(users.size(), result.size());
        assertEquals(user1.getEmail(), result.get(0).getEmail());
        assertEquals(user2.getEmail(), result.get(1).getEmail());

        verify(userRepository).findAll();
    }

    @Test
    void userService_getById_returnUser() {
        long id = 1;
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getById(id);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());

        verify(userRepository).findById(id);

    }

    @Test
    void userService_getByEmail_returnUser() {
        String email = "user@gmail.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.getByEmail(email);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository).findByEmail(email);
    }

    @Test
    void userService_delete_userFoundAndDeleted() {
        long id = 1L;
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.delete(id);

        verify(userRepository).findById(id);
        verify(userRepository).delete(user);

    }
}