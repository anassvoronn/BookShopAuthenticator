package org.nastya.service.mapper;

import org.nastya.dto.UserDTO;
import org.nastya.entity.User;
import org.nastya.service.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO mapToUserFormDTO(User user) {
        Assert.notNull(user, "user must not be null");
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    @Override
    public User mapToUser(UserDTO userDTO) {
        Assert.notNull(userDTO, "userDTO must not be null");
        return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword());
    }
}
