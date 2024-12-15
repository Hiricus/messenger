package com.pavmaxdav.mymess.service;

import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<User> removeByLogin(String login) {
        Optional<User> optionalUser = userRepository.findUserByLogin(login);
        if (optionalUser.isPresent()) {
            userRepository.removeByLogin(login);
        }

        return optionalUser;
    }
}
