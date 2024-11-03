package org.nastya.service;

import org.nastya.dto.UserDTO;
import org.nastya.entity.User;

public interface UserMapper {

    UserDTO mapToUserFormDTO(User user);

    User mapToUser(UserDTO userDTO);
}
