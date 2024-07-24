package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Discussions;
import com.example.engagement_platform.model.Events;
import com.example.engagement_platform.repository.DiscussionRepository;
import com.example.engagement_platform.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscussionServiceImpl implements DiscussionService{

    @Autowired
    private DiscussionRepository discussionRepository;

    @Override
    public List<Discussions> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    @Override
    public Discussions createDiscussion(Discussions discussions) {
        return discussionRepository.save(discussions);
    }

    @Override
    public Discussions getDiscussionById(Long discussionId) {
        Optional<Discussions> discussionFromDb = discussionRepository.findById(discussionId);
        if (discussionFromDb.isPresent()){
            return discussionFromDb.get();
        }else {
          throw new RuntimeException("Discussion not Found");
        }
    }

    @Override
    public void deleteDiscussionById(Long discussionId) {
        Optional<Discussions> discussionFrDb = discussionRepository.findById(discussionId);
        if (discussionFrDb.isPresent()){
            discussionRepository.deleteById(discussionId);
        }else{
            throw new RuntimeException("Discussion not found");
        }
    }

    @Override
    public void updateDiscussionById(Discussions discussions, Long discussionId) {
        Optional<Discussions> eventFrDatabase = discussionRepository.findById(discussionId);
        if (eventFrDatabase.isPresent()){
            discussionRepository.save(discussions);
        }else {
            throw new RuntimeException("Discussion not found");
        }
    }

}
