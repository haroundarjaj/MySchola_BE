package com.dartech.myschola.controller;

import com.dartech.myschola.dto.AuthResponseDto;
import com.dartech.myschola.dto.LoginDto;
import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.service.AuthenticationService;
import com.dartech.myschola.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    AuthenticationController (
            AuthenticationService authenticationService,
            UserService userService
    ) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!!!!!";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){

        String token = authenticationService.login(loginDto);

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);

        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    User saveAppUser(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }


}
