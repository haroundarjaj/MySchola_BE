package com.dartech.myschola.controller;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.dto.UserResponseDto;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.service.UserService;
import com.dartech.myschola.utils.security.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .email("user@gmail.com")
                .password("123456789")
                .build();
        user = User.builder()
                .email("user@gmail.com")
                .password("123456789")
                .roles(new ArrayList<>())
                .build();
    }

    @Test
    void userController_save_returnUser() throws Exception {
        when(userService.save(any(UserDto.class))).thenReturn(user);

        ResultActions result = mockMvc.perform(post("/api/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.password").value(userDto.getPassword()));
    }

    @Test
    void userController_update_returnUser() throws Exception {
        when(userService.update(any(UserDto.class))).thenReturn(user);

        ResultActions response = mockMvc.perform(put("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.password").value(userDto.getPassword()));

    }

    @Test
    void userController_getUserById_returnUser() throws Exception {
        long id = 1L;

        when(userService.getById(any(Long.class))).thenReturn(user);

        ResultActions response = mockMvc.perform(get("/api/user/get-by-id/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.password").value(userDto.getPassword()));
    }

    @Test
    void userController_getUserByEmail_returnUser() throws Exception {
        when(userService.getByEmail(userDto.getEmail())).thenReturn(user);
        
        ResultActions response = mockMvc.perform(
                get("/api/user/get-by-email/{email}", userDto.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
        );
        
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.password").value(userDto.getPassword()));
    }

    @Test
    void userController_getAllUsers_returnUsers() throws Exception {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .email(user.getEmail())
                .build();
        List<UserResponseDto> users = List.of(userResponseDto);

        when(userService.getAll()).thenReturn(users);

        ResultActions response = mockMvc.perform(
                get("/api/user/all")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()))
                .andExpect(jsonPath("$.[0].email").value(user.getEmail()));
    }

    @Test
    void userController_deleteUserById_returnOk() throws Exception {
        long id = 1L;
        doNothing().when(userService).delete(id);

        ResultActions response = mockMvc.perform(
                delete("/api/user/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }
}