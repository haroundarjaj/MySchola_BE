package com.dartech.myschola.controller;

import com.dartech.myschola.dto.RoleDto;
import com.dartech.myschola.entity.Role;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/save")
    Role save(@RequestBody RoleDto roleDto) { return roleService.save(roleDto); }

    @PutMapping("/update")
    Role update(@RequestBody RoleDto roleDto) { return roleService.update(roleDto); }

    @GetMapping("/get-by-id/{id}")
    Role getById(@PathVariable long id) { return roleService.getById(id); }

    @GetMapping("/get-by-code/{code}")
    Role getByCode(@PathVariable String code) { return roleService.getByCode(code); }

    @GetMapping("/all")
    List<Role> getAll() { return roleService.getAll(); }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> delete(@PathVariable int id) {
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
