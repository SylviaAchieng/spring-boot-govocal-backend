package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Projects;
import com.example.engagement_platform.model.dto.response.ProjectsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Base64;

public class ProjectMapperDecorator implements ProjectMapper{
    @Autowired
    @Qualifier("delegate")
    private ProjectMapper projectMapper;

    @Override
    public ProjectsDto toDto(Projects projects) {
        ProjectsDto mappedDto = projectMapper.toDto(projects);

        byte[] image = projects.getImage();
        if (image!=null && (image.length>0)){
            mappedDto.setBase64EncodedImage(Base64.getEncoder().encodeToString(image));
        }
        return mappedDto;
    }

    @Override
    public Projects toEntity(ProjectsDto projectsDto) {
        Projects mappedEntity = projectMapper.toEntity(projectsDto);

        String base64EncodedImage = projectsDto.getBase64EncodedImage();
        if (base64EncodedImage!=null && !base64EncodedImage.isEmpty()){
            mappedEntity.setImage(Base64.getDecoder().decode(base64EncodedImage));
        }
        return mappedEntity;
    }
}
