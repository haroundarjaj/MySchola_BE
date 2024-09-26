package com.dartech.myschola.controller;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app_user")
public class AppUserController {

    private final UserService userService;

    @Autowired
    AppUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    User saveAppUser(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping("/update")
    User updateAppUser(@RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @GetMapping("/id=?")
    User getAppUserById(@RequestParam int id) {
        return userService.getById(id);
    }

    @GetMapping("/all")
    List<User> getAllAppUsers() {
        return userService.getAll();
    }

    @DeleteMapping("/delete/id=?")
    ResponseEntity<User> deleteAppUserById(@RequestParam int id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
