package com.dartech.myschola.controller;

import com.dartech.myschola.entity.AppUser;
import com.dartech.myschola.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app_user")
public class AppUserController {

    AppUserService appUserService;

    @Autowired
    AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/save")
    AppUser saveAppUser(AppUser appUser) {
        return appUserService.saveAppUser(appUser);
    }
}
