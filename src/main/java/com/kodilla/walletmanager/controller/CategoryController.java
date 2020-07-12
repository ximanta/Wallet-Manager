package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService service;

    @PostMapping("/create")
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
