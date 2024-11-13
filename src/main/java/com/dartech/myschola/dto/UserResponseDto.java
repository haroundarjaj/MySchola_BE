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
public class UserResponseDto {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String gender;
    private String birthDate;
    private byte[] imageData;
    private boolean isActive;
    private String activeState;
    private String createdAt;
    private List<RoleDto> roles = new ArrayList<>();
}
