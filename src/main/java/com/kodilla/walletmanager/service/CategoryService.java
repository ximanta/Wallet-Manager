package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.mapper.CategoryMapper;
import com.kodilla.walletmanager.repository.CategoryRepository;
import com.kodilla.walletmanager.service.transaction.TransactionServiceCRUD;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceCRUD.class);

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CategoryDto create(final CategoryDto dto){
        if (ToolsManager.isCategoryDtoCorrect(dto)){
            Category entity = mapper.mapToEntity(dto);
            Category category = checkCategorySave(entity);
            return mapper.mapToDto(category);
        }else {
            LOGGER.error("Valid body",new RuntimeException());
            throw new RuntimeException("Valid body");
        }
    }

    public List<CategoryDto> getAll(String type){
        List<Category> list = repository.findAll();
        List<CategoryDto> dtos = mapper.mapToDtos(list);
        return ToolsManager.sortByTypeC(dtos,type);
    }

    public CategoryDto update(CategoryDto dto){
        return mapper.mapToDto(updateMechanic(dto));
    }

    public boolean delete(final long id){
        Optional<Category> optional = repository.findById(id);
        if (optional.isPresent()){
            Category category = optional.get();
            repository.delete(category);
            LOGGER.info("Category has been deleted");
            return !repository.existsById(id);
        }else {
            LOGGER.error("Cannot find Category by id");
            throw new RuntimeException("Cannot find Category by id");
        }
    }

    private Category updateMechanic(CategoryDto dto){
        Optional<Category> optional = repository.findById(dto.getId());
        if (ToolsManager.isCategoryDtoCorrect(dto) && optional.isPresent()){
            Category category = optional.get();
            category.setName(dto.getName());
            category.setType(dto.getType());
            return checkCategorySave(category);
        }else {
            LOGGER.error("Cannot find Category by id or Valid body ");
            throw new RuntimeException("Cannot find Category by id or Valid body");
        }
    }

    private Category checkCategorySave(Category category){
        try{
            Category formDb = repository.save(category);
            LOGGER.info("User has been saved");
            return formDb;
        }catch (Exception e){
            LOGGER.error("Save error", e);
            throw new RuntimeException();
        }
    }
}
