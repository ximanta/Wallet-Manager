package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.mapper.CategoryMapper;
import com.kodilla.walletmanager.repository.CategoryRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        CategoryDto categoryDto = factory.makeCategoryDto(ClassesFactory.COMPLETE);

        //When
        CategoryDto fromDb = service.create(categoryDto);
        repository.deleteById(fromDb.getId());

        //Then
        assertFalse(repository.existsById(fromDb.getId()));
        assertEquals("Test",categoryDto.getName());
        assertEquals(TransactionType.REVENUES,fromDb.getType());
    }

    @Test
    public void getAll() {
        //Given
        Category A = factory.makeCategory(ClassesFactory.COMPLETE);
        Category B = factory.makeCategory(ClassesFactory.COMPLETE);

        //When
        Category Db1 = repository.save(A);
        Category Db2 = repository.save(B);

        List<CategoryDto> dtos = service.getAll(null);
        repository.delete(Db1);
        repository.delete(Db2);

        //Then
        assertFalse(repository.existsById(Db1.getId()));
        assertFalse(repository.existsById(Db2.getId()));
        assertFalse(dtos.isEmpty());
    }

    @Test
    public void get() {
        //Given
        Category category = factory.makeCategory(ClassesFactory.COMPLETE);
        Category fromDb = repository.save(category);

        //When
        CategoryDto categoryDto = service.get(fromDb.getId());
        repository.delete(fromDb);

        //Then
        assertFalse(repository.existsById(fromDb.getId()));
        assertEquals("Test",categoryDto.getName());
        assertEquals(TransactionType.REVENUES,categoryDto.getType());
    }

    @Test
    public void update() {
        //Given
        Category category = factory.makeCategory(ClassesFactory.COMPLETE);
        Category fromDb = repository.save(category);
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

    @Test
    public void delete() {
        //When
        Category category = factory.makeCategory(ClassesFactory.COMPLETE);
        Category fromDb = repository.save(category);

        //When
        service.delete(fromDb.getId());

        //Then
        assertFalse(repository.existsById(fromDb.getId()));
    }
}