package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Project;
import com.example.engagement_platform.model.dto.response.ProjectsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Base64;

public class ProjectMapperDecorator implements ProjectMapper{
    @Autowired
    @Qualifier("delegate")
    private ProjectMapper projectMapper;

    @Override
    public ProjectsDto toDto(Project project) {
        ProjectsDto mappedDto = projectMapper.toDto(project);
        mappedDto.setProjectId(project.getProjectId());
        byte[] image = project.getImage();
        if (image!=null && (image.length>0)){
            mappedDto.setBase64EncodedImage(Base64.getEncoder().encodeToString(image));
        }
        return mappedDto;
    }

    @Override
    public Project toEntity(ProjectsDto projectsDto) {
        Project mappedEntity = projectMapper.toEntity(projectsDto);
        mappedEntity.setProjectId(projectsDto.getProjectId());
        String base64EncodedImage = projectsDto.getBase64EncodedImage();
        if (base64EncodedImage!=null && !base64EncodedImage.isEmpty()){
            mappedEntity.setImage(Base64.getDecoder().decode(base64EncodedImage));
        }
        return mappedEntity;
    }
}
