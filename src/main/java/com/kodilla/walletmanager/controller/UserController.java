package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto dto){
        return service.create(dto);
    }

    @GetMapping("/{login}/{password}")
    public UserDto get(@PathVariable String login, @PathVariable String password){
        return service.get(login,password);
    }

    @PutMapping("")
    public UserDto update(@RequestBody UserDto dto,
                          @RequestParam(value = "convert")boolean convert){
        return service.update(dto, convert);
    }

    @DeleteMapping("/{id}/{password}")
    public boolean delete(@PathVariable long id, @PathVariable String password) {
        return service.delete(id,password);
    }
}
