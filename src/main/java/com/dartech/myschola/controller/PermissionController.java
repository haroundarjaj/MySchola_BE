package com.dartech.myschola.controller;

import com.dartech.myschola.dto.PermissionDto;
import com.dartech.myschola.entity.Permission;
import com.dartech.myschola.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/save")
    public Permission save(@RequestBody PermissionDto permissionDto) {
        return permissionService.save(permissionDto);
    }

    @PutMapping("/update")
    public Permission update(@RequestBody PermissionDto permissionDto) {
        return permissionService.update(permissionDto);
    }

    @GetMapping("/get-by-id/{id}")
    public Permission getById(@PathVariable("id") long id) {
        return permissionService.getById(id);
    }

    @GetMapping("/all")
    public List<Permission> getAll() {
        return permissionService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Permission> delete(@PathVariable("id") long id) {
        permissionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
