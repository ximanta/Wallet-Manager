package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.mapper.CategoryMapper;
import com.kodilla.walletmanager.repository.CategoryRepository;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository repository;

    @Autowired
    CategoryMapper mapper;

    public CategoryDto create(final CategoryDto dto){
        Category entity = mapper.mapToEntity(dto);
        if (entity == null){
            return new CategoryDto();
        }else {
            Category category = repository.save(entity);
            return mapper.mapToDto(category);
        }
    }

    public List<CategoryDto> getAll(String type){
        List<Category> list = repository.findAll();
        List<CategoryDto> dtos = mapper.mapToDtos(list);
        return ToolsManager.sortByTypeC(dtos,type);
    }

    public CategoryDto get(long id){
        Optional<Category> category = repository.findById(id);
        if (category.isPresent()){
            return mapper.mapToDto(category.get());
        }else {
            return new CategoryDto();
        }
    }

    public CategoryDto update(CategoryDto dto){
        return mapper.mapToDto(updateMechanic(dto));
    }

    public boolean delete(final long id){
        Optional<Category> optional = repository.findById(id);
        if (optional.isPresent()){
            Category category = optional.get();
            repository.delete(category);
            return !repository.existsById(id);
        }else {
            throw new RuntimeException("Cannot find Transaction by id");
        }
    }

    private Category updateMechanic(CategoryDto dto){
        Optional<Category> optional = repository.findById(dto.getId());
        boolean isPresent = optional.isPresent();
        boolean isTitle = dto.getName() != null;
        boolean isType = dto.getType() != null;
        if (isPresent && isTitle && isType){
            Category category = optional.get();
            category.setName(dto.getName());
            category.setType(dto.getType());
            return repository.save(category);
        }else {
            throw new RuntimeException("Cannot find Category by id");
        }
    }
}
