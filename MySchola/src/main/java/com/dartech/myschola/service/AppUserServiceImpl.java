package com.dartech.myschola.service;

import com.dartech.myschola.dto.AppUserDto;
import com.dartech.myschola.entity.AppUser;
import com.dartech.myschola.mapper.AppUserMapper;
import com.dartech.myschola.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{

    AppUserRepository appUserRepository;
    AppUserMapper appUserMapper;

    @Autowired
    AppUserServiceImpl(
            AppUserRepository appUserRepository,
            AppUserMapper appUserMapper
    ) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
    }

    @Override
    public AppUser saveAppUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser updateAppUser(AppUser appUser) {
        return null;
    }

    @Override
    public AppUser getAppUserById(int id) {
        return null;
    }

    @Override
    public AppUser getAppUserByUsername(String username) {
        return null;
    }

    @Override
    public void deleteAppUserById(int id) {

    }

    @Override
    public List<AppUser> getAllAppUsers() {
        return List.of();
    }

}
