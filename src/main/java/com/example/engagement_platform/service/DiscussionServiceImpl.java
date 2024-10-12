package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.repository.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscussionServiceImpl implements DiscussionService{

    @Autowired
    private DiscussionRepository discussionRepository;

    @Override
    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    @Override
    public Discussion createDiscussion(Discussion discussions) {
        return discussionRepository.save(discussions);
    }

    @Override
    public Discussion getDiscussionById(Long discussionId) {
        Optional<Discussion> discussionFromDb = discussionRepository.findById(discussionId);
        if (discussionFromDb.isPresent()){
            return discussionFromDb.get();
        }else {
          throw new RuntimeException("Discussion not Found");
        }
    }

    @Override
    public void deleteDiscussionById(Long discussionId) {
        Optional<Discussion> discussionFrDb = discussionRepository.findById(discussionId);
        if (discussionFrDb.isPresent()){
            discussionRepository.deleteById(discussionId);
        }else{
            throw new RuntimeException("Discussion not found");
        }
    }

    @Override
    public void updateDiscussionById(Discussion discussions, Long discussionId) {
        Optional<Discussion> eventFrDatabase = discussionRepository.findById(discussionId);
        if (eventFrDatabase.isPresent()){
            discussionRepository.save(discussions);
        }else {
            throw new RuntimeException("Discussion not found");
        }
    }

}
