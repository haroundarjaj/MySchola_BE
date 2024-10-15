package com.dartech.myschola.controller;

import com.dartech.myschola.dto.PermissionDto;
import com.dartech.myschola.entity.Permission;
import com.dartech.myschola.service.PermissionService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PermissionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermissionService permissionService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private Permission permission;
    private PermissionDto permissionDto;

    @BeforeEach
    void setUp() {
        permission = Permission.builder()
                .action("CREATE")
                .subject("USER")
                .build();
        permissionDto = PermissionDto.builder()
                .action("CREATE")
                .subject("USER")
                .build();
    }

    @Test
    void permissionController_save_returnPermission() throws Exception {
        when(permissionService.save(any(PermissionDto.class))).thenReturn(permission);

        ResultActions result = mockMvc.perform(post("/api/permission/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(permissionDto))
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value(permissionDto.getSubject()))
                .andExpect(jsonPath("$.action").value(permissionDto.getAction()));

    }

    @Test
    void permissionController_update_returnPermission() throws Exception {
        when(permissionService.update(any(PermissionDto.class))).thenReturn(permission);

        ResultActions response = mockMvc.perform(
                put("/api/permission/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permissionDto))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value(permissionDto.getSubject()))
                .andExpect(jsonPath("$.action").value(permissionDto.getAction()));
    }

    @Test
    void permissionController_getById_returnPermission() throws Exception {
        long id = 1L;

        when(permissionService.getById(id)).thenReturn(permission);

        ResultActions response = mockMvc.perform(
                get("/api/permission/get-by-id/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value(permissionDto.getSubject()))
                .andExpect(jsonPath("$.action").value(permissionDto.getAction()));
    }

    @Test
    void permissionController_getAll_returnPermissions() throws Exception {
        List<Permission> permissions = List.of(permission);

        when(permissionService.getAll()).thenReturn(permissions);

        ResultActions response = mockMvc.perform(
                get("/api/permission/all")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(permissions.size()))
                .andExpect(jsonPath("$[0].subject").value(permission.getSubject()))
                .andExpect(jsonPath("$[0].action").value(permission.getAction()));
    }

    @Test
    void permissionController_delete_returnOk() throws Exception {
        long id = 1L;

        doNothing().when(permissionService).delete(id);

        ResultActions response = mockMvc.perform(
                delete("/api/permission/delete/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }
}