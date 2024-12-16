package com.pavmaxdav.mymess.service;

import com.pavmaxdav.mymess.entity.Chat;
import com.pavmaxdav.mymess.entity.Message;
import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.repository.ChatRepository;
import com.pavmaxdav.mymess.repository.MessageRepository;
import com.pavmaxdav.mymess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
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

    // Получения чата из бд
    @Transactional
    public Optional<Chat> getChat(Integer id) {
        Optional<Chat> optionalChat = chatRepository.findById(id);
        return optionalChat;
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
            chatRepository.delete(optionalChat.get());
        }
        return optionalChat;
    }

    // Добавление/удаление пользователей
    @Transactional
    public Optional<User> addUserToChat(Integer chatId, String userLogin) {
        Optional<User> optionalUser = userRepository.findUserByLogin(userLogin);
        Optional<Chat> optionalChat = chatRepository.findById(chatId);

        // Если не нашли пользователя или чата - возвращаем пустой Optional
        if (optionalChat.isEmpty() || optionalUser.isEmpty()) {
            return Optional.empty();
        }

        User user = optionalUser.get();
        Chat chat = optionalChat.get();
        // Если чат личный и участников двое или больше - возвращаем пустой Optional
        if (!chat.isGroup() && (chat.getUserCount() >= 2)) {
            return Optional.empty();
        }

        user.addToChat(chat);
        return optionalUser;
    }
    @Transactional
    public Optional<User> removeUserFromChat(Integer chatId, String userLogin) {
        Optional<User> optionalUser = userRepository.findUserByLogin(userLogin);
        Optional<Chat> optionalChat = chatRepository.findById(chatId);

        // Если не нашли пользователя или чата или же чат личный - возвращаем пустой Optional
        if (optionalUser.isEmpty() || optionalChat.isEmpty() || !optionalChat.get().isGroup()) {
            return Optional.empty();
        }

        User user = optionalUser.get();
        Chat chat = optionalChat.get();
        user.removeFromChat(chat);

        return optionalUser;
    }

    // Написать в чат
    @Transactional
    public Optional<Message> sendMessageToChat(String messageContent, String authorLogin, Integer chatId) {
        // Находим автора и чат
        Optional<User> optionalUser = userRepository.findUserByLogin(authorLogin);
        Optional<Chat> optionalChat = chatRepository.findById(chatId);

        // Если не нашли пользователя или нужный чат - возвращаем пустой Optional
        if (optionalChat.isEmpty() || optionalUser.isEmpty()) {
            return Optional.empty();
        }

        Chat chat = optionalChat.get();
        User author = optionalUser.get();
        // Если пользователь не находится данном чате - возвращаем пустой Optional
        if (!author.getChats().contains(chat)) {
            return Optional.empty();
        }

        // Создаём сообщение и добавляем его в список сообщений чата
        Message message = new Message(messageContent, author, chat);
        chat.addMessage(message);
        return Optional.of(message);
    }

    // Удалить сообщение из чата (также передаётся логин пользователя, пославшего запрос)
    @Transactional
    public Optional<Message> removeMessageFromChat(Integer messageId, String authorLogin) {
        Optional<User> optionalUser = userRepository.findUserByLogin(authorLogin);
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        // Если не нашли пользователя или сообщение - возвращаем пустой Optional
        if (optionalUser.isEmpty() || optionalMessage.isEmpty()) {
            return Optional.empty();
        }

        User author = optionalUser.get();
        Message message = optionalMessage.get();
        // Если пользователь не автор сообщения - возвращаем пустой Optional
        if (!author.equals(message.getAuthor())) {
            return Optional.empty();
        }

        // Получаем нужный чат и удаляем сообщение из него
        Chat chat = message.getChat();
        chat.removeMessage(message);
        return Optional.of(message);
    }
}
