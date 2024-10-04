package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Events;
import com.example.engagement_platform.model.Issues;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.repository.EventRepository;
import com.example.engagement_platform.repository.IssueRepository;
import com.example.engagement_platform.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssuesService{

    @Autowired
    private IssueRepository issueRepository;
    private LocationRepository locationRepository;

    @Override
    public List<Issues> getAllIssues() {
        return issueRepository.findAll();
    }


    @Override
    public Issues getIssueById(Long issueId) {
        Optional<Issues> issueFromDb = issueRepository.findById(issueId);
        if (issueFromDb.isPresent()){
            return issueFromDb.get();
        }else {
          throw new RuntimeException("Issue not Found");
        }
    }

    @Override
    public void deleteIssueById(Long issueId) {
        Optional<Issues> issueFrDb = issueRepository.findById(issueId);
        if (issueFrDb.isPresent()){
            issueRepository.deleteById(issueId);
        }else{
            throw new RuntimeException("Issue not found");
        }
    }

    @Override
    public void updateIssueById(Issues issues, Long issueId) {
        Optional<Issues> issueFrDatabase = issueRepository.findById(issueId);
        if (issueFrDatabase.isPresent()){
            issueRepository.save(issues);
        }else {
            throw new RuntimeException("Issue not found");
        }
    }

    @Override
    public GenericResponseV2<Issues> createIssue(Issues issues) {
        try {
            Issues response = issueRepository.save(issues);
            return GenericResponseV2.<Issues>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully created an issue")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Issues>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create issue")
                    ._embedded(null)
                    .build();
        }
    }

}
