package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.enums.CategoriesEnum;
import com.example.engagement_platform.mappers.DiscussionMapper;
import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.DiscussionViews;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.DiscussionDto;
import com.example.engagement_platform.repository.DiscussionRepository;
import com.example.engagement_platform.repository.DiscussionViewsRepository;
import com.example.engagement_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscussionServiceImpl implements DiscussionService{

    private final DiscussionRepository discussionRepository;
    private final DiscussionMapper discussionMapper;
    private final UserRepository userRepository;
    private final DiscussionViewsRepository discussionViewsRepository;

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
            User user = userRepository.findByFullName(discussionDto.getUser().getFullName()).orElseThrow(() -> new RuntimeException("User not found"));
            discussionDto.setUser(UserDto.builder()
                            .userId(user.getUserId())
                            .fullName(user.getFullName())
                            .userType(user.getUserType())
                    .build());
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
    public GenericResponseV2<DiscussionDto> getDiscussionById(Long discussionId, Long userId) {
        try {
            Discussion discussionFromDb = discussionRepository.findByDiscussionId(discussionId).orElseThrow(() -> new RuntimeException("Discussion not found"));
            boolean hasUserViewed = discussionViewsRepository.existsByDiscussionIdAndUserId(discussionId, userId);
            if (!hasUserViewed) {
                discussionFromDb.setViewCount(discussionFromDb.getViewCount() + 1);
                discussionRepository.save(discussionFromDb);
                // Save the user view record
                DiscussionViews viewRecord = DiscussionViews.builder()
                        .discussionId(discussionId)
                        .userId(userId)
                        .viewedAt(LocalDateTime.now())
                        .build();
                discussionViewsRepository.save(viewRecord);
            }
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

    @Override
    public GenericResponseV2<List<DiscussionDto>> getDiscussionsByCategory(CategoriesEnum category) {
        try {
            List<Discussion> discussions = discussionRepository.findAllByCategory(category);
            List<DiscussionDto> response = discussions.stream().map(discussionMapper::toDto).toList();
            return GenericResponseV2.<List<DiscussionDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Discussion retrieved successfully")
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
}
