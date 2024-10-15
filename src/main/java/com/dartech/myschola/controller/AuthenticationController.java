package com.dartech.myschola.controller;

import com.dartech.myschola.dto.AuthResponseDto;
import com.dartech.myschola.dto.LoginDto;
import com.dartech.myschola.dto.ResetPasswordRequestDto;
import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.entity.PasswordResetToken;
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

        AuthResponseDto authResponseDto = authenticationService.login(loginDto);

        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    User saveAppUser(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok("reset_link_sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDto request) {
        userService.resetPassword(request);
        return ResponseEntity.ok("password_reset_successfully");
    }

    @GetMapping("/get-token-info/{token}")
    public PasswordResetToken getTokenInfo(@PathVariable String token) {
        return userService.getTokenInfo(token);
    }


}
