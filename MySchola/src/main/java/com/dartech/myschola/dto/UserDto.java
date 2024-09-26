package com.dartech.myschola.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String gender;
    private String birthDate;
    private byte[] imageData;
    private boolean isActive;
    private List<RoleDto> roles = new ArrayList<>();
}
