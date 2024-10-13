package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Issue;
import com.example.engagement_platform.model.dto.response.IssueDto;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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

        mappedDto.setImage(imageMapper.toDto(issue.getIssueImage()));
        mappedDto.setLocationId(issue.getLocation().getLocationId());
        mappedDto.setUserId(issue.getUser().getUserId());
        return mappedDto;
    }

    @Override
    public Issue toEntity(IssueDto issueDto) {
        Issue mappedEntity = issueMapper.toEntity(issueDto);
        mappedEntity.setIssueImage(imageMapper.toEntity(issueDto.getImage()));
        mappedEntity.setLocation(locationRepository.findByLocationId(issueDto.getLocationId()).orElseThrow(() -> new RuntimeException("Location not found")));
        mappedEntity.setUser(userRepository.findByUserId(issueDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));

        return mappedEntity;
    }
}
