package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Image;
import com.example.engagement_platform.model.dto.request.ImageDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(value = ImageMapperDecorator.class)
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    ImageDto toDto(Image image);

    Image toEntity(ImageDto imageDto);
}
