package com.example.engagement_platform.service;

import com.example.engagement_platform.auth.JwtService;
import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.enums.EmailTemplateName;
import com.example.engagement_platform.mappers.UserMapper;
import com.example.engagement_platform.model.*;
import com.example.engagement_platform.model.dto.AuthResponseDto;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.request.AuthRequest;
import com.example.engagement_platform.model.dto.request.PublicServantDto;
import com.example.engagement_platform.model.dto.response.LocationDto;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.PublicServantRepository;
import com.example.engagement_platform.repository.TokenRepository;
import com.example.engagement_platform.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final LocationRepository locationRepository;
    private final PublicServantRepository publicServantRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    private String activationUrl = "http://localhost:4200/activate-account";


    @Override
    public GenericResponseV2<List<UserDto>> getAllUsers() {
        try {
            List<UserDto> users = userRepository.findAll()
                    .stream()
                    .map(userMapper::toDto)
                    .toList();
            return GenericResponseV2.<List<UserDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully retrieved users")
                    ._embedded(users)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<UserDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve users")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<AuthResponseDto> authenticate(AuthRequest request) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var claims = new HashMap<String, Object>();
            var user = ((User)auth.getPrincipal());
            claims.put("fullName", user.getFullName());
            var jwtToken = jwtService.generateToken(claims, user);
            AuthResponseDto response = AuthResponseDto.builder()
                    .token(jwtToken)
                    .user(user)
                    .build();
            return GenericResponseV2.<AuthResponseDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("success")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<AuthResponseDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to generate token")
                    ._embedded(null)
                    .build();
        }
    }

    @Transactional
    @Override
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email");
        }
        var user = userRepository.findByUserId(savedToken.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    @Override
    public GenericResponseV2<List<UserDto>> getUserByLocationId(Long locationId) {
        try {
            Location location = Location.builder()
                    .locationId(locationId)
                    .build();
            List<User> users = userRepository.findAllByLocation(location);
            List<UserDto> response = users.stream().map(userMapper::toDto).toList();
            return GenericResponseV2.<List<UserDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("users retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<UserDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("unable to retrieve users")
                    ._embedded(null)
                    .build();
        }
    }

    @Transactional
    @Override
    public GenericResponse addUsers(UserDto newUser) {
        try {
            // Fetch the location details based on the locationId from the Users entity
            Location location = locationRepository.findByLocationId(newUser.getLocation().getLocationId())
                    .orElse(Location.builder().locationId(1L).county("Nairobi").subCounty("Nairobi").build());

            newUser.setLocation(LocationDto.builder()
                            .locationId(location.getLocationId())
                    .build());
            User userToSave = userMapper.toEntity(newUser);

            // encrypt the password
            String encryptedPassword = passwordEncoder.encode(userToSave.getPassword());
            userToSave.setPassword(encryptedPassword);
            User createdUser = userRepository.save(userToSave);
            log.info("saving user {}", createdUser);
            if (newUser.getUserType().equals(UserType.PUBLIC_SERVANT)) {

                PublicServantDto publicServant = newUser.getPublicServant();
                if (publicServant == null) {
                    return GenericResponse.builder()
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
                log.info("saved successfully");

                String subject = "Welcome to GOVocal – Your Voice Matters!";

                String body = "<html>" +
                        "<body style='font-family: Arial, sans-serif;'>" +
                        "<p>Dear " + newUser.getFullName() + ",</p>" +
                        "<p>Welcome to <strong>GOVocal</strong>! We're excited to have you on board as we work together to make civic engagement more accessible and impactful.</p>" +
                        "<p>With GOVocal, you can:</p>" +
                        "<ul>" +
                        "<li> Raise and track issues directly with government representatives</li>" +
                        "<li> Participate in discussions and collaborate on community projects</li>" +
                        "<li> Stay informed about important civic events and updates</li>" +
                        "</ul>" +
                        "<p>To get started, log in to your account here: <a href='http://localhost:4200/login'>Login</a></p>" +
                        "<p>If you have any questions, feel free to reach out. We’re here to ensure your voice is heard!</p>" +
                        "<br>" +
                        "<p>Best regards,</p>" +
                        "<p><strong>The GOVocal Team</strong></p>" +
                        "<p><a href='http://localhost:4200/'>Visit GOVocal</a> | <a href='mailto:achiengsylvia157@gmail.com'>Contact Support</a></p>" +
                        "</body></html>";

                emailService.sendEmail(newUser.getEmail(), subject, body, "achiengsylvia157@gmail.com", "GOVocal");
                log.info("sending email");

            }

            return GenericResponse.builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully added a new user")
                    ._embedded(Collections.singletonList(createdUser))
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponse.builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create a new user: " + e.getMessage())
                    .build();
        }

    }

    @Override
    public GenericResponseV2<UserDto> getUserById(Long userId) {
        try {
            User userFromDb = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
            UserDto response = userMapper.toDto(userFromDb);
            return GenericResponseV2.<UserDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("User retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<UserDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("User not found")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> deleteUserById(Long userId) {
        try {
            User userFromDb = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
            userRepository.delete(userFromDb);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("User deleted successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to delete user")
                    ._embedded(false)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> updateUserById(Long userId, UserDto userDto) {
        try {
            User userToBeSaved = userMapper.toEntity(userDto);

            // encrypt the password
            String encryptedPassword = passwordEncoder.encode(userToBeSaved.getPassword());
            userToBeSaved.setPassword(encryptedPassword);

            User savedUser = userRepository.save(userToBeSaved);
            userMapper.toDto(savedUser);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("User details updated successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to update user details")
                    ._embedded(false)
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


