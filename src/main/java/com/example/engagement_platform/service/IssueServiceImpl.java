package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Issue;
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
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }


    @Override
    public Issue getIssueById(Long issueId) {
        Optional<Issue> issueFromDb = issueRepository.findById(issueId);
        if (issueFromDb.isPresent()){
            return issueFromDb.get();
        }else {
          throw new RuntimeException("Issue not Found");
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
    public GenericResponseV2<Issue> createIssue(Issue issues) {
        try {
            Issue response = issueRepository.save(issues);
            return GenericResponseV2.<Issue>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully created an issue")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Issue>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create issue")
                    ._embedded(null)
                    .build();
        }
    }

}
