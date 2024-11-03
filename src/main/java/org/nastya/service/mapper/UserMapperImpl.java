package org.nastya.service.mapper;

import org.nastya.dto.UserDTO;
import org.nastya.entity.User;
import org.nastya.service.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO mapToUserFormDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    @Override
    public User mapToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword());
    }
}
