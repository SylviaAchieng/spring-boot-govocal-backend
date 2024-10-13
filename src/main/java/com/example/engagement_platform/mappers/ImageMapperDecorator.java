package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Image;
import com.example.engagement_platform.model.dto.request.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Base64;

public class ImageMapperDecorator implements ImageMapper{
    @Autowired
    @Qualifier("delegate")
    private ImageMapper imageMapper;

    @Override
    public ImageDto toDto(Image image) {
        ImageDto mappedDto = imageMapper.toDto(image);

        byte[] imageUrl = image.getImageFile();
        if (imageUrl!=null && (imageUrl.length>0)){
            mappedDto.setBase64EncodedImage(Base64.getEncoder().encodeToString(imageUrl));
        }
        return mappedDto;
    }

    @Override
    public Image toEntity(ImageDto imageDto) {
        Image mappedEntity = imageMapper.toEntity(imageDto);

        String base64EncodedImage = imageDto.getBase64EncodedImage();
        if (base64EncodedImage!=null && !base64EncodedImage.isEmpty()){
            mappedEntity.setImageFile(Base64.getDecoder().decode(base64EncodedImage));
        }
        return mappedEntity;
    }
}
