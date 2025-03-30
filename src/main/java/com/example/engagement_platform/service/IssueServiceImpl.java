package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.GenericResponseV3;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.enums.IssueStatusEnum;
import com.example.engagement_platform.enums.NotificationStatusEnum;
import com.example.engagement_platform.mappers.ImageMapper;
import com.example.engagement_platform.mappers.IssueMapper;
import com.example.engagement_platform.model.Issue;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.Notification;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.response.IssueDto;
import com.example.engagement_platform.model.dto.response.IssueStats;
import com.example.engagement_platform.model.dto.response.NotificationDto;
import com.example.engagement_platform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssuesService{

    private final IssueRepository issueRepository;
    private final LocationRepository locationRepository;
    private final ImageMapper imageMapper;
    private final IssueMapper issueMapper;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;


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
            Issue issue = issueRepository.findByIssueId(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));
            IssueDto response = issueMapper.toDto(issue);
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
    public GenericResponseV2<List<IssueDto>> getIssueByLocationId(Long locationId) {
        try {
            Location location = Location.builder()
                    .locationId(locationId)
                    .build();
            List<Issue> issues = issueRepository.findAllByLocation(location);
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

    private static IssueStats getIssueStats(List<IssueDto> issues) {
        // get the stats
        Map<IssueStatusEnum, List<IssueDto>> groupedByStatus = issues
                .stream()
                .collect(Collectors.groupingBy(IssueDto::getStatus));

        IssueStats stat = IssueStats.builder().statusCount(new HashMap<>()).build();
        groupedByStatus.forEach((issueStatusEnum, issueDtos) -> stat.getStatusCount().put(issueStatusEnum, issueDtos.size()));

        Arrays.stream(IssueStatusEnum.values()) // [CREATE, COMPLETED, CANCELLED, ...]
                .forEach(issueStatusEnum -> {
                    if (!stat.getStatusCount().containsKey(issueStatusEnum)) { // if status not account for, default to 0
                        stat.getStatusCount().put(issueStatusEnum, 0);
                    }
                });
        return stat;
    }

    @Override
    public GenericResponseV2<List<IssueDto>> getAllIssuesByStatus(IssueStatusEnum status, Long userId) {
        try {
            User user = User.builder()
                    .userId(userId)
                    .build();
            List<Issue> issueByStatus = issueRepository.findAllByStatusAndUser(status, user);
            List<IssueDto> response = issueByStatus.stream().map(issueMapper::toDto).toList();
            return GenericResponseV2.<List<IssueDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Issues retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<IssueDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve issues")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV3<List<IssueDto>, IssueStats> getIssueByUserId(Long userId) {
        try {
            User user = User.builder()
                    .userId(userId)
                    .build();

            List<Issue> issues = issueRepository.findAllByUser(user);
            List<IssueDto> response = issues.stream().map(issueMapper::toDto).toList();

            IssueStats stat = getIssueStats(response);

            return GenericResponseV3.<List<IssueDto>, IssueStats>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Issue retrieved successfully")
                    .metadata(stat) // adding the metadata
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV3.<List<IssueDto>, IssueStats>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve issue")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> deleteIssueById(Long issueId) {
        try {
            Issue issueFromDb = issueRepository.findByIssueId(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));
            issueRepository.delete(issueFromDb);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Issue deleted successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to delete issue")
                    ._embedded(false)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> updateIssueById(IssueDto issueDto, Long issueId) {
        try {
            Issue issueToSave = issueMapper.toEntity(issueDto);
            issueToSave.setCreatedAt(Date.valueOf(LocalDate.now()));
            Issue savedIssue = issueRepository.save(issueToSave);
            issueMapper.toDto(savedIssue);

            if (issueDto.getNotificationId() != null) {
                Optional<Notification> optionalNotification = notificationRepository.findById(issueDto.getNotificationId());

                if (optionalNotification.isPresent()) {
                    // Update the existing notification
                    Notification existingNotification = optionalNotification.get();
                    existingNotification.setDescription("Issue update alert!");
                    existingNotification.setType(issueDto.getStatus().name());
                    existingNotification.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
                    existingNotification.setStatus(NotificationStatusEnum.SENT);
                    notificationRepository.save(existingNotification);
                }
            } else {
                // Create a new notification if none exists
                NotificationDto notificationDto = new NotificationDto();
                notificationDto.setType(issueDto.getStatus().name());
                notificationDto.setDescription("Issue update alert!");
                notificationDto.setStatus(NotificationStatusEnum.SENT);
                notificationDto.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
                notificationService.createNotification(notificationDto);
            }

            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Issue updated successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to update issue")
                    ._embedded(false)
                    .build();
        }


    }

    @Override
    public GenericResponseV2<IssueDto> createIssue(IssueDto issueDto) {
        try {
            Issue issueToBeSaved = issueMapper.toEntity(issueDto);
            IssueStatusEnum status = issueDto.getStatus() !=null ? issueDto.getStatus(): IssueStatusEnum.CREATED;
            issueToBeSaved.setStatus(status);
            issueToBeSaved.setCreatedAt(Date.valueOf(LocalDate.now()));

            // Check if user exists before setting it
            if (issueDto.getUser() != null && issueDto.getUser().getUserId() != null) {
                Optional<User> userOpt = userRepository.findByUserId(issueDto.getUser().getUserId());
                issueToBeSaved.setUser(userOpt.orElse(null)); // Assign user if found, else keep null for anonymous
            } else {
                issueToBeSaved.setUser(null);
            }

            Issue savedIssue = issueRepository.save(issueToBeSaved);
            IssueDto response = issueMapper.toDto(savedIssue);
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setDescription("New issue alert");
            notificationDto.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
            notificationDto.setType(IssueStatusEnum.CREATED.name());
            notificationDto.setStatus(NotificationStatusEnum.SENT);
            User user = savedIssue.getUser();
            if (user != null) {
                notificationDto.setUserId(user.getUserId());
                notificationDto.setLocationId(user.getLocation().getLocationId());
            }
            notificationService.createNotification(notificationDto);
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
