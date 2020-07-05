package com.kodilla.walletmanager.domain;

import com.kodilla.walletmanager.repository.IconsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IconsTest {
    @Autowired
    IconsRepository iconsRepository;

    @Test
    public void createCompleteRecordTest(){
        //Given
        Icons icons = new Icons();
        icons.setUrl("Test URL");

        //When
        Icons fromDb = iconsRepository.save(icons);
        iconsRepository.delete(icons);

        //Then
        assertNotNull(fromDb.getId());
        assertEquals("Test URL",fromDb.getUrl());
        assertFalse(iconsRepository.existsById(fromDb.getId()));
    }

}