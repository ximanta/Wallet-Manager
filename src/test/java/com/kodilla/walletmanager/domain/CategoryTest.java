package com.kodilla.walletmanager.domain;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void createCompleteRecordTest(){
        //Given
        Category category = new Category();
        category.setName("Test");
        category.setType(TransactionType.EXPENSES);

        categoryRepository.save(category);

        //When
        Category fromDb = categoryRepository.getOne(category.getId());
        categoryRepository.delete(category);

        //Then
        assertEquals("Test",fromDb.getName());
        assertEquals(TransactionType.EXPENSES,fromDb.getType());
        assertFalse(categoryRepository.existsById(category.getId()));
    }

}