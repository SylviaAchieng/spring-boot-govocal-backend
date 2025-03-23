package com.example.engagement_platform.mappers;


import com.example.engagement_platform.model.ContactUs;
import com.example.engagement_platform.model.dto.response.ContactUsDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(value = ContactUsMapperDecorator.class)
public interface ContactUsMapper {
    ContactUsMapper INSTANCE = Mappers.getMapper(ContactUsMapper.class);
    @Mapping(source = "location", target = "location", ignore = true)
    ContactUsDto toDto(ContactUs contactUs);

    @Mapping(source = "location", target = "location", ignore = true)
    ContactUs toEntity(ContactUsDto contactUsDto);
}
