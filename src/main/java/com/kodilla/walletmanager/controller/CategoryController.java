package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService service;

    @PostMapping(value = "/create",consumes = APPLICATION_JSON_VALUE)
    public CategoryDto create(@RequestBody CategoryDto dto){
        return service.create(dto);
    }

    @GetMapping("/getAll")
    public List<CategoryDto> getAll(@RequestParam(value = "type",required = false) String type){
        return service.getAll(type);
    }

    @GetMapping("/get/{id}")
    public CategoryDto get(@PathVariable long id){
        return service.get(id);
    }

    @PutMapping("/update")
    public CategoryDto update(@RequestBody CategoryDto dto){
        return service.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable long id){
        return service.delete(id);
    }
}
