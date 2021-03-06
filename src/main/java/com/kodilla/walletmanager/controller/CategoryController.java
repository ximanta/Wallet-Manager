package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;

    private CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto dto){
        return service.create(dto);
    }

    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(value = "type",required = false) String type){
        return service.getAll(type);
    }

    @PutMapping
    public CategoryDto update(@RequestBody CategoryDto dto){
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable long id){
        return service.delete(id);
    }
}
