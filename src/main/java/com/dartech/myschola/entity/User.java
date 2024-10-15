package com.dartech.myschola.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Transactional
public class User extends BaseEntity implements UserDetails {

    @TableGenerator(
            name = "idGeneratorTable",
            allocationSize = 1,
            initialValue = 1)
    @Id
    @GeneratedValue(
            strategy=GenerationType.TABLE,
            generator="idGeneratorTable")
    private Long id;

    //Credentials
    private String email;
    private String password;

    //General info
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String gender;
    private String birthDate;
    @Lob
    private byte[] imageData;
    private boolean isActive = false;
    private boolean isApproved = false;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getCode()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isApproved;
    }
}
