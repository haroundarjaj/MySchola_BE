package com.dartech.myschola.controller;

import com.dartech.myschola.dto.RoleDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser(username = "admin@gmail.com")
@Transactional
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class RoleControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MockMvc mockMvc;

    private RoleDto roleDto;


    @BeforeEach
    void setUp() {

        roleDto = RoleDto.builder()
                .name("admin")
                .code("ADMIN")
                .build();
    }

    @Test
    void roleControllerIntegration_save_returnRole() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/role/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleDto))
        );

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(roleDto.getName()))
                .andExpect(jsonPath("$.code").value(roleDto.getCode()));
    }
}
