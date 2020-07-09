package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperTest {
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ClassesFactory classesFactory;

    @Test
    public void mapToEntity() {
        //Given
        CategoryDto categoryDto = classesFactory.makeCategoryDto(ClassesFactory.COMPLETE);

        //When
        Category category = categoryMapper.mapToEntity(categoryDto);

        //Then
        assertNull(category.getId());
        assertEquals("Test",category.getName());
        assertEquals(TransactionType.REVENUES,category.getType());
        assertEquals(0,category.getTransactions().size());
    }

    @Test
    public void mapToDto() {
        Category category = classesFactory.makeCategory(ClassesFactory.COMPLETE);

        //When
        CategoryDto categoryDto = categoryMapper.mapToDto(category);

        //Then
        assertNull(categoryDto.getId());
        assertEquals("Test",categoryDto.getName());
        assertEquals(TransactionType.REVENUES,categoryDto.getType());
        assertEquals(0,categoryDto.getTransactionDtos().size());
    }

    @Test
    public void mapToDtos() {
        //Given
        List<Category> categories = new ArrayList<>();

        Category category1 = classesFactory.makeCategory(ClassesFactory.COMPLETE);
        Category category2 = classesFactory.makeCategory(ClassesFactory.COMPLETE);

        categories.add(category1);
        categories.add(category2);

        //When
        List<CategoryDto> categoryDtos = categoryMapper.mapToDtos(categories);

        //Then
        assertEquals(2,categoryDtos.size());
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