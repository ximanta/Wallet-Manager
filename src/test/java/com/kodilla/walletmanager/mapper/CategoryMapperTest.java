package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.dto.TransactionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperTest {
    @Autowired
    CategoryMapper categoryMapper;

    @Test
    public void mapToEntity() {
        //Given
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test");
        categoryDto.setType(TransactionType.REVENUES);

        //When
        Category category = categoryMapper.mapToEntity(categoryDto);

        //Then
        assertNull(category.getId());
        assertEquals("Test",category.getName());
        assertEquals(TransactionType.REVENUES,category.getType());
    }

    @Test
    public void mapToDto() {
        Category category = new Category();
        category.setName("Test");
        category.setType(TransactionType.REVENUES);

        //When
        CategoryDto categoryDto = categoryMapper.mapToDto(category);

        //Then
        assertNull(categoryDto.getId());
        assertEquals("Test",categoryDto.getName());
        assertEquals(TransactionType.REVENUES,categoryDto.getType());
    }

    @Test
    public void mapToDtos() {
        //Given
        List<Category> categories = new ArrayList<>();

        Category category1 = new Category();
        category1.setName("Test 1");
        category1.setType(TransactionType.REVENUES);

        Category category2 = new Category();
        category2.setName("Test 2");
        category2.setType(TransactionType.EXPENSES);

        categories.add(category1);
        categories.add(category2);

        //When
        List<CategoryDto> categoryDtos = categoryMapper.mapToDtos(categories);

        CategoryDto categoryDto1 = categoryDtos.get(0);
        CategoryDto categoryDto2 = categoryDtos.get(1);

        //Then
        assertNull(categoryDto1.getId());
        assertEquals("Test 1",categoryDto1.getName());
        assertEquals(TransactionType.REVENUES,categoryDto1.getType());

        assertNull(categoryDto2.getId());
        assertEquals("Test 2",categoryDto2.getName());
        assertEquals(TransactionType.EXPENSES,categoryDto2.getType());
    }

    @Test
    public void mapFromEmptyListToDtos() {
        //Given
        List<Category> categories = new ArrayList<>();

        //When
        List<CategoryDto> categoryDtos = categoryMapper.mapToDtos(categories);

        //Then
        assertTrue(categoryDtos.isEmpty());
    }
}