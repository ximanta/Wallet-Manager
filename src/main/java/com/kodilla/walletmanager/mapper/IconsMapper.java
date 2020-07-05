package com.kodilla.walletmanager.mapper;

import com.kodilla.walletmanager.domain.Icons;
import com.kodilla.walletmanager.dto.IconsDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IconsMapper {
    public Icons mapToEntity(IconsDto iconsDto){
        Icons icons = new Icons();
        icons.setId(iconsDto.getId());
        icons.setUrl(iconsDto.getUrl());

        return icons;
    }

    public IconsDto mapToDto(Icons icons){
        IconsDto iconsDto = new IconsDto();
        iconsDto.setId(icons.getId());
        iconsDto.setUrl(icons.getUrl());

        return iconsDto;
    }

    public List<IconsDto> mapToDtos(List<Icons> icons){
        return icons.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
