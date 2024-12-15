package com.pavmaxdav.mymess.service;

import com.pavmaxdav.mymess.entity.Chat;
import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.repository.ChatRepository;
import com.pavmaxdav.mymess.repository.MessageRepository;
import com.pavmaxdav.mymess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
public class ChatService {
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    // Создание пустого чата
    @Transactional
    public Optional<Chat> createEmptyChat(String name, boolean isGroup) {
        Chat chat = new Chat(name, isGroup);
        chatRepository.save(chat);
        return Optional.of(chat);
    }

    // Удаление чата
    @Transactional
    public Optional<Chat> removeChat(Integer id) {
        Optional<Chat> optionalChat = chatRepository.findById(id);
        if (optionalChat.isPresent()) {
            for (User user : optionalChat.get().getUsers()) {
                user.getChats().remove(optionalChat.get());
            }
            optionalChat.get().getUsers().clear();
            chatRepository.delete(optionalChat.get());
        }
        return optionalChat;
    }

    // Добавление/удаление пользователей
    @Transactional
    public Optional<User> addUserToChat(Integer chatId, String userLogin) {
        Optional<User> optionalUser = userRepository.findUserByLogin(userLogin);
        Optional<Chat> optionalChat = chatRepository.findById(chatId);

        if (optionalUser.isPresent() && optionalChat.isPresent()) {
            optionalUser.get().addToChat(optionalChat.get());
        }
        return optionalUser;
    }
    @Transactional
    public Optional<User> removeUserFromChat(Integer chatId, String userLogin) {
        Optional<User> optionalUser = userRepository.findUserByLogin(userLogin);
        Optional<Chat> optionalChat = chatRepository.findById(chatId);

        if (optionalUser.isPresent() && optionalChat.isPresent()) {
            optionalUser.get().removeFromChat(optionalChat.get());
        }
        return optionalUser;
    }
}
