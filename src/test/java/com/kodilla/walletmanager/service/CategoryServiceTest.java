package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.mapper.CategoryMapper;
import com.kodilla.walletmanager.repository.CategoryRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    CategoryService service;

    @Autowired
    CategoryRepository repository;

    @Autowired
    CategoryMapper mapper;

    @Autowired
    ClassesFactory factory;

    @Test
    public void create() {
        //Given
        CategoryDto categoryDto = factory.categoryDto();

        //When
        CategoryDto fromDb = service.create(categoryDto);
        repository.deleteById(fromDb.getId());

        //Then
        assertFalse(repository.existsById(fromDb.getId()));
        assertEquals("Test",categoryDto.getName());
        assertEquals(TransactionType.REVENUES,fromDb.getType());
    }

    @Test(expected = RuntimeException.class)
    public void createValidBody() {
        CategoryDto categoryDto = new CategoryDto();
        CategoryDto fromDb = service.create(categoryDto);
        repository.deleteById(fromDb.getId());
    }

    @Test
    public void getAll() {
        //Given
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i <= 3; i++){
            categories.add(repository.save(factory.category()));
        }

        //When
        List<CategoryDto> dtos = service.getAll(null);
        System.out.println(dtos);
        for (Category category: categories) {
            repository.delete(category);
        }

        //Then
        for (Category category: categories) {
            assertFalse(repository.existsById(category.getId()));
        }
        assertFalse(dtos.isEmpty());
    }

    @Test
    public void update() {
        //Given
        Category fromDb = repository.save(factory.category());
        CategoryDto dto = mapper.mapToDto(fromDb);
        dto.setName("Test update");
        dto.setType(TransactionType.EXPENSES);

        //When
        CategoryDto update = service.update(dto);
        repository.delete(fromDb);

        //Then
        assertFalse(repository.existsById(fromDb.getId()));
        assertEquals(fromDb.getId(),update.getId());
        assertEquals("Test update",update.getName());
        assertEquals(TransactionType.EXPENSES,update.getType());
    }

    @Test(expected = RuntimeException.class)
    public void validUpdate(){
        CategoryDto dto = new CategoryDto();
        service.update(dto);
    }

    @Test
    public void delete() {
        //When
        Category fromDb = repository.save(factory.category());

        //When
        service.delete(fromDb.getId());

        //Then
        assertFalse(repository.existsById(fromDb.getId()));
    }

    @Test(expected = RuntimeException.class)
    public void  validDelete(){
        service.delete(-2);
    }
}