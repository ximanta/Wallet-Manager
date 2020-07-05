package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.Icons;
import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.dto.IconsDto;
import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.repository.IconsRepository;
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
public class IconsMapperTest {
    @Autowired
    IconsMapper iconsMapper;

    @Test
    public void mapToEntity() {
        //Given
        IconsDto iconsDto = new IconsDto();
        iconsDto.setUrl("Test URL");

        //When
        Icons icons = iconsMapper.mapToEntity(iconsDto);

        //Then
        assertNull(icons.getId());
        assertEquals("Test URL",icons.getUrl());
    }

    @Test
    public void mapToDto() {
        //Given
        Icons icons = new Icons();
        icons.setUrl("Test URL");

        //When
        IconsDto iconsDto = iconsMapper.mapToDto(icons);

        //Then
        assertNull(iconsDto.getId());
        assertEquals("Test URL",iconsDto.getUrl());
    }

    @Test
    public void mapToDtos() {
        //Given
        List<Icons> iconsList = new ArrayList<>();

        Icons icons1 = new Icons();
        Icons icons2 = new Icons();

        iconsList.add(icons1);
        iconsList.add(icons2);

        //When
        List<IconsDto> iconsDtos = iconsMapper.mapToDtos(iconsList);

        //Then
        assertEquals(2,iconsDtos.size());
    }
    @Test
    public void mapFromEmptyListToDtos() {
        //Given
        List<Icons> iconsList = new ArrayList<>();

        //When
        List<IconsDto> iconsDtos = iconsMapper.mapToDtos(iconsList);

        //Then
        assertTrue(iconsDtos.isEmpty());
    }

}