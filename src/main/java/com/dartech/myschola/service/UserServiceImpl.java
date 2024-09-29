package com.dartech.myschola.service;

import com.dartech.myschola.dto.UserDto;
import com.dartech.myschola.entity.User;
import com.dartech.myschola.mapper.UserMapper;
import com.dartech.myschola.repository.UserRepository;
import com.dartech.myschola.utils.OperationLogGenerator;
import com.dartech.myschola.utils.exception.PredefinedExceptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final OperationLogGenerator<User> operationLogGenerator;

    @Autowired
    UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            OperationLogGenerator<User> operationLogGenerator
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.operationLogGenerator = operationLogGenerator;
    }

    @Override
    public User save(UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        user = operationLogGenerator.generateCreationLog(user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(false);
        return userRepository.save(user);
    }

    @Override
    public User update(UserDto userDto) {
        System.out.println(userDto);
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
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw PredefinedExceptions.notFoundException;
        }
        user.ifPresent(userRepository::delete);
    }

}
