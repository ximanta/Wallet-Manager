package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    @Autowired
    TransactionMapper transactionMapper;

    public Category mapToEntity(CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setType(categoryDto.getType());

        return category;
    }

    public CategoryDto mapToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setType(category.getType());


        return categoryDto;
    }

    public List<CategoryDto> mapToDtos(List<Category> categories){
        return categories.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
