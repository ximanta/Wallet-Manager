package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.dto.UserDto;
import com.kodilla.walletmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping("/getAll")
    public List<UserDto> getAll(){
        return service.getAll();
    }

    @PutMapping("/update")
    public UserDto update(@RequestBody UserDto dto){
        return service.update(dto);
    }
}
