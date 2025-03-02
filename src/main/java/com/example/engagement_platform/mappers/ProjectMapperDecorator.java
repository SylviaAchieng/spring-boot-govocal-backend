package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.Project;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.LocationDto;
import com.example.engagement_platform.model.dto.response.ProjectsDto;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Base64;

public class ProjectMapperDecorator implements ProjectMapper{
    @Autowired
    @Qualifier("delegate")
    private ProjectMapper projectMapper;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ProjectsDto toDto(Project project) {
        ProjectsDto mappedDto = projectMapper.toDto(project);
        mappedDto.setProjectId(project.getProjectId());

        User user = project.getUser();
        if (user != null){
            mappedDto.setUser(UserDto.builder()
                    .userId(user.getUserId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .userType(user.getUserType())
                    .build());
        }

        // custom mapping for location
        Location location = project.getLocation();
        if (location != null){
            mappedDto.setLocation(LocationDto.builder()
                    .locationId(location.getLocationId())
                    .address(location.getAddress())
                    .county(location.getCounty())
                    .subCounty(location.getSubCounty())
                    .build());
        }
        byte[] image = project.getImage();
        if (image!=null && (image.length>0)){
            mappedDto.setBase64EncodedImage(Base64.getEncoder().encodeToString(image));
        }
        mappedDto.setStartDate(project.getStartDate());
        mappedDto.setEndDate(project.getEndDate());
        return mappedDto;
    }

    @Override
    public Project toEntity(ProjectsDto projectsDto) {
        Project mappedEntity = projectMapper.toEntity(projectsDto);
        mappedEntity.setProjectId(projectsDto.getProjectId());

        UserDto userDto = projectsDto.getUser();
        if (userDto!=null){
            Long userId = userDto.getUserId();
            User user = userRepository.findByUserId(userId)
                    .orElse(User.builder()
                            .userId(userId)
                            .build());
            mappedEntity.setUser(user);
        }

        LocationDto locationDto = projectsDto.getLocation();
        if (locationDto!=null){
            Long locationId = locationDto.getLocationId();
            Location location = locationRepository.findByLocationId(locationId)
                    .orElse(Location.builder()
                            .locationId(locationId)
                            .build());
            mappedEntity.setLocation(location);
        }
        String base64EncodedImage = projectsDto.getBase64EncodedImage();
        if (base64EncodedImage!=null && !base64EncodedImage.isEmpty()){
            mappedEntity.setImage(Base64.getDecoder().decode(base64EncodedImage));
        }

        return mappedEntity;
    }
}
