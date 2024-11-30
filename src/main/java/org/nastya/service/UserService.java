package org.nastya.service;

import org.nastya.dto.UserDTO;
import org.nastya.entity.User;
import org.nastya.repository.UserRepository;
import org.nastya.service.exception.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Optional<UserDTO> findByUsername(String username) {
        log.info("Finding user by username: {}", username);
        Optional<UserDTO> userDTO = userRepository.findByUsername(username)
                .map(userMapper::mapToUserFormDTO);
        log.info("User found: {}", userDTO.isPresent() ? userDTO.get() : "No user found");
        return userDTO;
    }

    public boolean existsByUsername(String username) {
        log.info("Checking existence of user by username: {}", username);
        boolean exists = userRepository.existsByUsername(username);
        log.info("User exists: {}", exists);
        return exists;
    }

    public UserDTO saveUser(UserDTO userDTO) {
        if (existsByUsername(userDTO.getUsername())) {
            log.warn("User with username {} already exists.", userDTO.getUsername());
            throw new UserAlreadyExistsException("User with username " + userDTO.getUsername() + " already exists.");
        }
        log.info("Saving user: {}", userDTO);
        User user = userMapper.mapToUser(userDTO);
        User savedUser = userRepository.save(user);
        log.info("User saved: {}", savedUser);
        return userMapper.mapToUserFormDTO(savedUser);
    }

    public void deleteUser(Integer id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
        log.info("User with id {} deleted", id);
    }
}
