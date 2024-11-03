package org.nastya.service;

import org.nastya.dto.UserDTO;
import org.nastya.entity.User;
import org.nastya.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::mapToUserFormDTO);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = userMapper.mapToUser(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.mapToUserFormDTO(savedUser);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
