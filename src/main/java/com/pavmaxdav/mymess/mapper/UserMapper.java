package com.pavmaxdav.mymess.mapper;

import com.pavmaxdav.mymess.dto.UserDTO;
import com.pavmaxdav.mymess.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO(user.getId(), user.getLogin(), user.getUserData().getAvatarImage());
        return userDTO;
    }
}
