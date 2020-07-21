package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.tools.ClassesFactory;
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
    CategoryMapper mapper;

    @Autowired
    ClassesFactory factory;

    @Test
    public void mapToEntity() {
        //Given
        CategoryDto categoryDto = factory.categoryDto();

        //When
        Category category = mapper.mapToEntity(categoryDto);

        //Then
        assertNull(category.getId());
        assertEquals("Test",category.getName());
        assertEquals(TransactionType.REVENUES,category.getType());
        assertEquals(0,category.getTransactions().size());
    }

    @Test
    public void mapToDto() {
        Category category = factory.category();

        //When
        CategoryDto categoryDto = mapper.mapToDto(category);

        //Then
        assertNull(categoryDto.getId());
        assertEquals("Test",categoryDto.getName());
        assertEquals(TransactionType.REVENUES,categoryDto.getType());
    }

    @Test
    public void mapToDtos() {
        //Given
        List<Category> categories = new ArrayList<>();
        for (int i = 1; i <= 3; i++){
            categories.add(factory.category());
        }

        //When
        List<CategoryDto> categoryDtos = mapper.mapToDtos(categories);

        //Then
        assertEquals(3,categoryDtos.size());
    }

    @Test
    public void mapFromEmptyListToDtos() {
        //Given
        List<Category> categories = new ArrayList<>();

        //When
        List<CategoryDto> categoryDtos = mapper.mapToDtos(categories);

        //Then
        assertTrue(categoryDtos.isEmpty());
    }

}