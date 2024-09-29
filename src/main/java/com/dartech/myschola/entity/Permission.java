package com.dartech.myschola.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Permission extends BaseEntity {

    @Id
    @TableGenerator(
            name = "idGeneratorTable",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "idGeneratorTable")
    private Long id;

    private String action;
    private String subject;
}
