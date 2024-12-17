package com.pavmaxdav.mymess.service;

import com.pavmaxdav.mymess.entity.Chat;
import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.entity.UserData;
import com.pavmaxdav.mymess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Добавить пользователя
    @Transactional
    public Optional<User> addUser(String login, String password) {
        // Если пользователь с таким логином уже существует - вернуть пустой Optional
        if (userRepository.findUserByLogin(login).isPresent()) {
            return Optional.empty();
        }

        User user = new User(login, password);
        userRepository.save(user);
        return Optional.of(user);
    }
    @Transactional
    public Optional<User> addUser(User user) {
        // Если пользователь с таким логином уже существует - вернуть пустой Optional
        if (userRepository.findUserByLogin(user.getLogin()).isPresent()) {
            return Optional.empty();
        }

        userRepository.save(user);
        return Optional.of(user);
    }

    // Добавить пользователю дополнительные данные
    @Transactional
    public Optional<UserData> addUserData(Integer userId, UserData userData) {
        Optional<User> optionalUser = userRepository.findById(userId);

        // Если такого пользователя нет - вернуть пустой Optional
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        User user = optionalUser.get();
        user.setUserData(userData);
        return Optional.of(userData);
    }

    // Поиск пользователя по логину
    @Transactional
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    // Удаление пользователя по логину
    @Transactional
    public Optional<User> removeByLogin(String login) {
        Optional<User> optionalUser = userRepository.findUserByLogin(login);
        if (optionalUser.isPresent()) {
            userRepository.removeByLogin(login);
        }

        return optionalUser;
    }

    @Transactional
    public List<Chat> getUsersChats(String login) {
        Optional<User> optionalUser = this.findUserByLogin(login);

        // Если такого пользователя нет - возвращаем пустой массив
        if (optionalUser.isEmpty()) {
            return new ArrayList<>();
        }

        // Получаем массив чатов пользователя и сортируем его по дате последней активности
        ArrayList<Chat> chats = new ArrayList<>(optionalUser.get().getChats());
        Collections.sort(chats, Comparator.comparing(Chat::getLastActive));
        Collections.reverse(chats);

        return chats;
    }
}
