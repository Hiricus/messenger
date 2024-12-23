package com.pavmaxdav.mymess.mapper;

import com.pavmaxdav.mymess.dto.UserDTO;
import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.entity.attached.Settings;
import com.pavmaxdav.mymess.entity.attached.Theme;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UserMapper {
    public UserDTO toDTO(User user, boolean withData) {
        if (!withData) {
            UserDTO userDTO = new UserDTO(user.getId(),
                    user.getLogin(),
                    Base64.getEncoder().encodeToString(user.getUserData().getAvatarImage()));
            userDTO.setSettings(null);
            return userDTO;
        } else {
            UserDTO userDTO = new UserDTO(user.getId(),
                    user.getLogin(),
                    Base64.getEncoder().encodeToString(user.getUserData().getAvatarImage()),
                    new Settings(Theme.LIGHT, user.getUserData().getSettings()));
            return userDTO;
        }
    }
}
