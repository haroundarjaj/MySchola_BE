package com.dartech.myschola.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetToken {

    @TableGenerator(
            name = "idGeneratorTable",
            allocationSize = 1,
            initialValue = 1)
    @Id
    @GeneratedValue(
            strategy=GenerationType.TABLE,
            generator="idGeneratorTable")
    private Long id;

    private String token;
    private LocalDateTime expirationTime;

    @OneToOne
    private User user;

}
