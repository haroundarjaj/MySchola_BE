package com.dartech.myschola.controller;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public User saveAppUser(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping("/update")
    public User updateAppUser(@RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @GetMapping("/get-by-id/{id}")
    public User getAppUserById(@PathVariable long id) {
        return userService.getById(id);
    }

    @GetMapping("/get-by-email/{email}")
    public User getAppUserByEmail(@PathVariable String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/all")
    public List<User> getAllAppUsers() {
        return userService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteAppUserById(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
