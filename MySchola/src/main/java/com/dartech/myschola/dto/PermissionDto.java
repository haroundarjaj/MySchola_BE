package com.dartech.myschola.dto;

import lombok.Data;

@Data
public class PermissionDto {

    private long id;
    private String action;
    private String subject;

}
