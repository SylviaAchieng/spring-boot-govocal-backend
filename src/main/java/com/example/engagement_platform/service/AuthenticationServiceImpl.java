package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.enums.EmailTemplateName;
import com.example.engagement_platform.mappers.UserMapper;
import com.example.engagement_platform.model.PublicServant;
import com.example.engagement_platform.model.Token;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.UserType;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.request.PublicServantDto;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.PublicServantRepository;
import com.example.engagement_platform.repository.TokenRepository;
import com.example.engagement_platform.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final LocationRepository locationRepository;
    private final PublicServantRepository publicServantRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    private String activationUrl = "http://localhost:4200/activate-account";

    @Override
    public GenericResponseV2<?> register(UserDto request) {
        try {
            // Fetch the location details based on the locationId from the Users entity
            locationRepository.findByLocationId(request.getLocation().getLocationId())
                    .orElseThrow(() -> new RuntimeException("Location not found"));

            User userToSave = userMapper.toEntity(request);

            User createdUser = userRepository.save(userToSave);
            sendValidationEmail(userToSave);
            if (request.getUserType().equals(UserType.PUBLIC_SERVANT)) {

                PublicServantDto publicServant = request.getPublicServant();
                if (publicServant == null) {
                    return GenericResponseV2.builder()
                            .status(ResponseStatusEnum.ERROR)
                            .message("Public servant details must be provided for PUBLIC_SERVANT user type")
                            .build();

                } else {
                    PublicServant servant = PublicServant.builder()
                            .user(createdUser)
                            .department(publicServant.getDepartment())
                            .position(publicServant.getPosition())
                            .build();
                    servant.setUser(createdUser);
                    publicServantRepository.save(servant);
                }

            }

            return GenericResponseV2.builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("User registration successful")
                    ._embedded(Collections.singletonList(createdUser))
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to register user")
                    .build();

        }
    }

    private void sendValidationEmail(User userToSave) throws MessagingException {
        var newToken = generateAndSaveActivationToken(userToSave);
        //send email
        emailService.sendSimpleMessage(
                userToSave.getEmail(),
                userToSave.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(User userToSave) {
        // generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(userToSave)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
