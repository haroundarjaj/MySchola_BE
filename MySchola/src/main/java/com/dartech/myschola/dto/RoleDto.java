package com.dartech.myschola.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDto {

    private long id;
    private String name;
    private String code;
    private String description;
    private List<PermissionDto> permissions = new ArrayList<>();
}
