package com.dartech.myschola.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private long id;
    private String name;
    private String code;
    private String description;
    private List<PermissionDto> permissions = new ArrayList<>();
}
