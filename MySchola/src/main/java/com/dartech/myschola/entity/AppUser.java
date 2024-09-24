package com.dartech.myschola.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.util.Date;

@Entity
@Table(name = "app_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //Credentials
    private String email;
    private String password;

    //General info
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String gender;
    private String birthdate;
    @Lob
    private byte[] imageData;

}
