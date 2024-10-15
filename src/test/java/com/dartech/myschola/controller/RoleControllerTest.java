package com.dartech.myschola.controller;

import com.dartech.myschola.dto.RoleDto;
import com.dartech.myschola.entity.Role;
import com.dartech.myschola.service.RoleService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private Role role;
    private RoleDto roleDto;

    @BeforeEach
    void setUp() {
        roleDto = RoleDto.builder()
                .name("admin")
                .code("ADMIN")
                .build();
        role = Role.builder()
                .name("admin")
                .code("ADMIN")
                .build();
    }

    @Test
    void roleController_save_returnRole() throws Exception {
        when(roleService.save(any(RoleDto.class))).thenReturn(role);

        ResultActions response = mockMvc.perform(
                post("/api/role/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDto))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(roleDto.getName()))
                .andExpect(jsonPath("$.code").value(roleDto.getCode()));
    }

    @Test
    void roleController_update_returnRole() throws Exception {
        when(roleService.update(any(RoleDto.class))).thenReturn(role);

        ResultActions response = mockMvc.perform(
                put("/api/role/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDto))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(roleDto.getName()))
                .andExpect(jsonPath("$.code").value(roleDto.getCode()));
    }

    @Test
    void roleController_getById_returnRole() throws Exception {
        long id = 1L;

        when(roleService.getById(id)).thenReturn(role);

        ResultActions response = mockMvc.perform(
                get("/api/role/get-by-id/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(role.getName()))
                .andExpect(jsonPath("$.code").value(role.getCode()));
    }

    @Test
    void roleController_getByCode_returnRole() throws Exception {
        String code = "ADMIN";

        when(roleService.getByCode(code)).thenReturn(role);

        ResultActions response = mockMvc.perform(
                get("/api/role/get-by-code/{code}", code)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(role.getName()))
                .andExpect(jsonPath("$.code").value(role.getCode()));
    }

    @Test
    void roleController_getAll_returnRoles() throws Exception {
        List<Role> roles = List.of(role);

        when(roleService.getAll()).thenReturn(roles);

        ResultActions response = mockMvc.perform(
                get("/api/role/all")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(roles.size()))
                .andExpect(jsonPath("$[0].name").value(role.getName()))
                .andExpect(jsonPath("$[0].code").value(role.getCode()));
    }

    @Test
    void roleController_delete_returnOk() throws Exception {
        long id = 1L;

        doNothing().when(roleService).delete(id);

        ResultActions response = mockMvc.perform(
                delete("/api/role/delete/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }
}