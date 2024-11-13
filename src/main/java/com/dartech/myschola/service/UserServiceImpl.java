package com.dartech.myschola.service;

import com.dartech.myschola.dto.ResetPasswordRequestDto;
import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.dto.UserResponseDto;
import com.dartech.myschola.entity.PasswordResetToken;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.mapper.UserMapper;
import com.dartech.myschola.repository.PasswordResetTokenRepository;
import com.dartech.myschola.repository.UserRepository;
import com.dartech.myschola.utils.EmailService;
import com.dartech.myschola.utils.OperationLogGenerator;
import com.dartech.myschola.utils.exception.CustomException;
import com.dartech.myschola.utils.exception.ExceptionType;
import com.dartech.myschola.utils.exception.PredefinedExceptions;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Value("${client-api}")
    private String clientApi;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final OperationLogGenerator<User> operationLogGenerator;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            OperationLogGenerator<User> operationLogGenerator,
            EmailService emailService,
            PasswordResetTokenRepository passwordResetTokenRepository
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.operationLogGenerator = operationLogGenerator;
        this.emailService = emailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public User save(UserDto userDto) {
        Optional<User> userTest = userRepository.findByEmail(userDto.getEmail());
        if(userTest.isPresent()) {
            throw  new CustomException("user_email_exists", "User email already exist", HttpStatus.CONFLICT);
        }
        userTest = userRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(userDto.getFirstName(), userDto.getLastName());
        if(userTest.isPresent()) {
            throw  new CustomException("user_names_exist", "First name and Last name combination already exist", HttpStatus.CONFLICT);
        }
        User user = userMapper.dtoToEntity(userDto);
        user = operationLogGenerator.generateCreationLog(user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(true);
        user.setApproved(false);
        user.setRoles(new ArrayList<>());
        User savedUser = userRepository.save(user);
        try {
            emailService.sendEmail(
                    "registration-email",
                    user.getEmail(),
                    "Account Registered Successfully",
                    Map.of("first_name", user.getFirstName())
            );
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
        return savedUser;
    }

    @Override
    public User update(UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        User userOld = userRepository.findById(user.getId()).orElseThrow(() -> PredefinedExceptions.notFoundException);
        user.setCreatedAt(userOld.getCreatedAt());
        user.setCreatedBy(userOld.getCreatedBy());
        user = operationLogGenerator.generateUpdateLog(user);
        return userRepository.save(user);
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(() -> PredefinedExceptions.notFoundException);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> PredefinedExceptions.notFoundException);
    }

    @Override
    public List<UserResponseDto> getAll() {
        List<User> users = userRepository.findAllByEmailNot("super@admin.com");
        List<UserResponseDto> userResponseDtos = users.stream().map(user -> {
            UserResponseDto userResponseDto = userMapper.entityToResponseDto(user);
            userResponseDto.setActiveState(user.isActive() ? "active" : "blocked");
            return userResponseDto;
        }).toList();
        return userResponseDtos;
    }

    @Override
    public void approveUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> PredefinedExceptions.notFoundException);
        user.setApproved(true);
        userRepository.save(user);
        try {
            emailService.sendEmail(
                    "account-approved-email",
                    user.getEmail(),
                    "Account Approved Successfully",
                    Map.of("first_name", user.getFirstName())
            );
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> PredefinedExceptions.notFoundException);

        Optional<PasswordResetToken> oldToken = passwordResetTokenRepository.findByUserId(user.getId());
        if(oldToken.isPresent()) {
            if(oldToken.get().getExpirationTime().isBefore(LocalDateTime.now())) {
                passwordResetTokenRepository.delete(oldToken.get());
                passwordResetTokenRepository.flush();
            } else {
                throw new CustomException(
                        "token_already_sent",
                        "Password reset token already sent to email",
                        HttpStatus.CONFLICT
                );
            }
        }
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .expirationTime(LocalDateTime.now().plusMinutes(2))
                .user(user)
                .build();

        passwordResetTokenRepository.save(passwordResetToken);

        String resetLink = clientApi + "/reset-password?token=" + token;
        try {
            emailService.sendEmail(
                    "reset-password-email",
                    user.getEmail(),
                    "Reset Account Password",
                    Map.ofEntries(
                            Map.entry("first_name", user.getFirstName()),
                            Map.entry("reset_link", resetLink)
                    )
            );
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequestDto request) {
        Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByToken(request.getToken());
        if ( resetToken.isEmpty() || resetToken.get().getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new CustomException(
                    ExceptionType.TOKEN_EXPIRED.getCode(),
                    ExceptionType.TOKEN_EXPIRED.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }

        PasswordResetToken token = resetToken.get();

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        passwordResetTokenRepository.delete(token);
    }

    @Override
    public void delete(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw PredefinedExceptions.notFoundException;
        }
        user.ifPresent(userRepository::delete);
    }

    @Override
    public PasswordResetToken getTokenInfo(String token) {
        System.out.println(token);
        return passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new CustomException(
                        ExceptionType.TOKEN_EXPIRED.getCode(),
                        ExceptionType.TOKEN_EXPIRED.getMessage(),
                        HttpStatus.BAD_REQUEST
                ));
    }

}
