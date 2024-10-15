package com.dartech.myschola.controller;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@ActiveProfiles("test")
@WithMockUser(username = "admin@gmail.com")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .email("user@gmail.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .roles(new ArrayList<>())
                .build();

        user = User.builder()
                .email("user@gmail.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .roles(new ArrayList<>())
                .build();
    }

    @Test
    void userControllerIntegration_save_returnUser() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()));
    }

    @Test
    void userControllerIntegration_update_returnUser() throws Exception {
        User savedUser = userRepository.save(user);

        userDto.setId(savedUser.getId());
        userDto.setEmail("admin@gmail.com");

        ResultActions response = mockMvc.perform(put("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        );

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()));
    }

    @Test
    void userControllerIntegration_getById_returnUser() throws Exception {
        User savedUser = userRepository.save(user);

        ResultActions response = mockMvc.perform(
                get("/api/user/get-by-id/{id}", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()));
    }

    @Test
    void userControllerIntegration_getByEmail_returnUser() throws Exception {
        User savedUser = userRepository.save(user);

        ResultActions response = mockMvc.perform(
                get("/api/user/get-by-email/{email}", savedUser.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()));
    }

    @Test
    void userControllerIntegration_getAll_returnUser() throws Exception {
        User savedUser = userRepository.save(user);

        ResultActions response = mockMvc.perform(get("/api/user/all")
                .contentType(MediaType.APPLICATION_JSON)
        );

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(savedUser.getId()))
                .andExpect(jsonPath("$[0].email").value(user.getEmail()))
                .andExpect(jsonPath("$[0].firstName").value(user.getFirstName()));
    }

    @Test
    void userControllerIntegration_delete_returnOk() throws Exception {
        User savedUser = userRepository.save(user);

        ResultActions response = mockMvc.perform(delete("/api/user/delete/{id}", savedUser.getId()));

        response.andDo(print())
                .andExpect(status().isOk());
    }
}
