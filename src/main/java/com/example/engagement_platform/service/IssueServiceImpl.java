package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.ImageMapper;
import com.example.engagement_platform.mappers.IssueMapper;
import com.example.engagement_platform.model.Image;
import com.example.engagement_platform.model.Issue;
import com.example.engagement_platform.model.dto.response.IssueDto;
import com.example.engagement_platform.repository.ImageRepository;
import com.example.engagement_platform.repository.IssueRepository;
import com.example.engagement_platform.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssuesService{

    private final IssueRepository issueRepository;
    private final LocationRepository locationRepository;
    private final ImageMapper imageMapper;
    private final IssueMapper issueMapper;
    private final ImageRepository imageRepository;


    @Override
    public GenericResponseV2<List<IssueDto>> getAllIssues() {
        try {
            List<Issue> issues = issueRepository.findAll();
            List<IssueDto> response = issues.stream().map(issueMapper::toDto).toList();
            return GenericResponseV2.<List<IssueDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Issue retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<IssueDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve issue")
                    ._embedded(null)
                    .build();
        }
    }


    @Override
    public GenericResponseV2<IssueDto> getIssueById(Long issueId) {
        try {
            Issue issueFromDb = issueRepository.findByIssueId(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));

            IssueDto response = issueMapper.toDto(issueFromDb);
            return GenericResponseV2.<IssueDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Issue retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<IssueDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve issue")
                    ._embedded(null)
                    .build();
        }


    }

    @Override
    public void deleteIssueById(Long issueId) {
        Optional<Issue> issueFrDb = issueRepository.findById(issueId);
        if (issueFrDb.isPresent()){
            issueRepository.deleteById(issueId);
        }else{
            throw new RuntimeException("Issue not found");
        }
    }

    @Override
    public void updateIssueById(Issue issues, Long issueId) {
        Optional<Issue> issueFrDatabase = issueRepository.findById(issueId);
        if (issueFrDatabase.isPresent()){
            issueRepository.save(issues);
        }else {
            throw new RuntimeException("Issue not found");
        }
    }

    @Override
    public GenericResponseV2<IssueDto> createIssue(IssueDto issueDto) {
        try {
            Issue issueToBeSaved = issueMapper.toEntity(issueDto);
            Image savedImage = imageRepository.save(issueToBeSaved.getIssueImage());
            issueToBeSaved.setIssueImage(savedImage);
            Issue savedIssue = issueRepository.save(issueToBeSaved);
            IssueDto response = issueMapper.toDto(savedIssue);
            return GenericResponseV2.<IssueDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully created an issue")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<IssueDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create issue")
                    ._embedded(null)
                    .build();
        }
    }

}
