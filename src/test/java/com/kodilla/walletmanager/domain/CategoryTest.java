package com.kodilla.walletmanager.domain;

import com.kodilla.walletmanager.domain.entities.Category;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.repository.CategoryRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTest {
    @Autowired
    CategoryRepository repository;

    @Autowired
    ClassesFactory factory;

    @Test
    public void createCompleteRecordTest(){
        //Given
        Category category = factory.category();
        repository.save(category);

        //When
        Category fromDb = repository.getOne(category.getId());
        repository.delete(category);

        //Then
        assertEquals("Test",fromDb.getName());
        assertEquals(TransactionType.REVENUES,fromDb.getType());
        assertTrue(fromDb.getTransactions().isEmpty());
        assertFalse(repository.existsById(category.getId()));
    }

    @Test(expected = ConstraintViolationException.class )
    public void crateIncompleteRecordTest(){
            Category category = new Category();
            Category fromDb = repository.save(category);
            assertFalse(repository.existsById(fromDb.getId()));
    }

}