package com.dartech.myschola.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserDto {

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private String gender;
    private String address;

}
