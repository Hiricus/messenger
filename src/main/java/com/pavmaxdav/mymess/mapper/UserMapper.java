package com.pavmaxdav.mymess.mapper;

import com.pavmaxdav.mymess.dto.UserDTO;
import com.pavmaxdav.mymess.entity.User;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO(user.getId(),
                user.getLogin(),
                Base64.getEncoder().encodeToString(user.getUserData().getAvatarImage()));
        return userDTO;
    }
}
