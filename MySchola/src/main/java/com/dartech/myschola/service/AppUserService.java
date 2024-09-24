package com.dartech.myschola.service;

import com.dartech.myschola.entity.AppUser;

import java.util.List;

public interface AppUserService {

    AppUser saveAppUser(AppUser appUser);
    AppUser updateAppUser(AppUser appUser);
    AppUser getAppUserById(int id);
    AppUser getAppUserByUsername(String username);
    List<AppUser> getAllAppUsers();
    void deleteAppUserById(int id);
}
