package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Issue;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.IssueDto;
import com.example.engagement_platform.model.dto.response.LocationDto;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Base64;

public class IssueMapperDecorator implements IssueMapper{
    @Autowired
    @Qualifier("delegate")
    private IssueMapper issueMapper;

    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public IssueDto toDto(Issue issue) {
        IssueDto mappedDto = issueMapper.toDto(issue);

        //mappedDto.setImage(imageMapper.toDto(issue.getIssueImage()));
        // custom mapping for user
        User user = issue.getUser();
        if (user != null){
            mappedDto.setUser(UserDto.builder()
                    .userId(user.getUserId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .userType(user.getUserType())
                    .build());
        }
        // custom mapping for location
        Location location = issue.getLocation();
        if (location != null){
            mappedDto.setLocation(LocationDto.builder()
                    .locationId(location.getLocationId())
                    .address(location.getAddress())
                    .county(location.getCounty())
                    .subCounty(location.getSubCounty())
                    .build());
        }
        byte[] image = issue.getImage();
        if (image!=null && (image.length>0)){
            mappedDto.setBase64EncodedImage(Base64.getEncoder().encodeToString(image));
        }
        return mappedDto;
    }

    @Override
    public Issue toEntity(IssueDto issueDto) {
        Issue mappedEntity = issueMapper.toEntity(issueDto);
        //mappedEntity.setIssueImage(imageMapper.toEntity(issueDto.getImage()));
        UserDto userDto = issueDto.getUser();
        if (userDto!=null){
            Long userId = userDto.getUserId();
            User user = userRepository.findByUserId(userId)
                    .orElse(User.builder()
                            .userId(userId)
                            .build());
            mappedEntity.setUser(user);
        }

        LocationDto locationDto = issueDto.getLocation();
        if (locationDto!=null){
            Long locationId = locationDto.getLocationId();
            Location location = locationRepository.findByLocationId(locationId)
                    .orElse(Location.builder()
                            .locationId(locationId)
                            .build());
            mappedEntity.setLocation(location);
        }
        String base64EncodedImage = issueDto.getBase64EncodedImage();
        if (base64EncodedImage!=null && !base64EncodedImage.isEmpty()){
            mappedEntity.setImage(Base64.getDecoder().decode(base64EncodedImage));
        }

        return mappedEntity;
    }
}
