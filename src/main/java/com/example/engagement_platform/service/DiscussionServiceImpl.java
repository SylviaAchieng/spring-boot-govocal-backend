package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.DiscussionMapper;
import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.dto.response.DiscussionDto;
import com.example.engagement_platform.repository.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscussionServiceImpl implements DiscussionService{

    private final DiscussionRepository discussionRepository;
    private final DiscussionMapper discussionMapper;

    @Override
    public GenericResponseV2<List<DiscussionDto>> getAllDiscussions() {
        try {
            List<DiscussionDto> response = discussionRepository.findAll()
                    .stream()
                    .map(discussionMapper::toDto)
                    .toList();
            return GenericResponseV2.<List<DiscussionDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Discussions retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<DiscussionDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve discussions")
                    ._embedded(null)
                    .build();
        }

    }

    @Override
    public GenericResponseV2<DiscussionDto> createDiscussion(DiscussionDto discussionDto) {
        try {
            Discussion discussionToBeSaved = discussionMapper.toEntity(discussionDto);
            Discussion savedDiscussion = discussionRepository.save(discussionToBeSaved);
            DiscussionDto response = discussionMapper.toDto(savedDiscussion);
            return GenericResponseV2.<DiscussionDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Discussion created successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<DiscussionDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create discussion")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<DiscussionDto> getDiscussionById(Long discussionId) {
        try {
            Discussion discussionFromDb = discussionRepository.findByDiscussionId(discussionId).orElseThrow(() -> new RuntimeException("Discussion not found"));
            DiscussionDto response = discussionMapper.toDto(discussionFromDb);
            return GenericResponseV2.<DiscussionDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Discussion retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<DiscussionDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve discussion")
                    ._embedded(null)
                    .build();
        }

    }

    @Override
    public GenericResponseV2<Boolean> deleteDiscussionById(Long discussionId) {
        try {
            Discussion discussionFromDb = discussionRepository.findByDiscussionId(discussionId).orElseThrow(() -> new RuntimeException("Discussion not found"));
            discussionRepository.delete(discussionFromDb);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Discussion deleted successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to delete discussion")
                    ._embedded(false)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> updateDiscussionById(DiscussionDto discussionDto, Long discussionId) {
        try {
            Discussion discussionToSave = discussionMapper.toEntity(discussionDto);
            Discussion savedDiscussion = discussionRepository.save(discussionToSave);
            discussionMapper.toDto(savedDiscussion);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Discussion updated successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to update discussion")
                    ._embedded(false)
                    .build();
        }
    }
}
