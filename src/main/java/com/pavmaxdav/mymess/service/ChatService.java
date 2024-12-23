package com.pavmaxdav.mymess.service;

import com.pavmaxdav.mymess.dto.MessageDTO;
import com.pavmaxdav.mymess.entity.AttachedResource;
import com.pavmaxdav.mymess.entity.Chat;
import com.pavmaxdav.mymess.entity.Message;
import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.mapper.MessageMapper;
import com.pavmaxdav.mymess.repository.ChatRepository;
import com.pavmaxdav.mymess.repository.MessageRepository;
import com.pavmaxdav.mymess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ChatService {
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private MessageMapper messageMapper;

    @Autowired
    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository, UserRepository userRepository, MessageMapper messageMapper) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
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
        Chat savedChat = chatRepository.save(chat);
        return Optional.of(savedChat);
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
            System.out.println("Can't find user in method: " + userLogin);
            return Optional.empty();
        }

        User user = optionalUser.get();
        Chat chat = optionalChat.get();
        // Если чат личный и участников двое или больше - возвращаем пустой Optional
        if (!chat.isGroup() && (chat.getUserCount() >= 2)) {
            System.out.println("Group mismatch for user: " + userLogin);
            System.out.println(chat.isGroup() + " " + chat.getUserCount());
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
    public Optional<Message> sendMessageToChat(String messageContent, String authorLogin, Integer chatId, AttachedResource attachedResource) {
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
        // В зависимости от наличия вложений сохраняем их или нет
        Message message;
        if (attachedResource == null) {
            message = new Message(messageContent, author, chat);
        } else {
//            System.out.println("Creating message with resource: " + attachedResource);
            message = new Message(messageContent, author, chat, attachedResource);
            attachedResource.setMessage(message);
        }
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

    // Получить все сообщения из чата, отсортированные по дате
    @Transactional
    public List<MessageDTO> getAllMessagesFromChat(Integer chatId) {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        // Если такого чата нет - возвращаем пустой список
        if (optionalChat.isEmpty()) {
            return new ArrayList<>();
        }

        List<Message> messages = messageRepository.findMessageByChat_IdOrderBySentAt(chatId);
        Collections.reverse(messages);
        ArrayList<MessageDTO> messageDTOS = new ArrayList<>();
        for (Message message : messages) {
            messageDTOS.add(messageMapper.toDTO(message));
        }

        return messageDTOS;
    }

    @Transactional
    public List<MessageDTO> getLastMessagesFromChat(Integer chatId, Integer lastN) {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        // Если такого чата нет - возвращаем пустой список
        if (optionalChat.isEmpty()) {
            return new ArrayList<>();
        }

        Pageable pageable = PageRequest.of(0, lastN, Sort.by("sentAt").descending());
        List<Message> messages = messageRepository.findMessageByChat_Id(chatId, pageable);

        ArrayList<MessageDTO> messageDTOS = new ArrayList<>();
        for (Message message : messages) {
            messageDTOS.add(messageMapper.toDTO(message));
        }

        return messageDTOS;
    }






    @Transactional
    public AttachedResource getResource(Integer id) {
        Message message = messageRepository.findById(id).get();
        System.out.println(message);
        return message.getAttachedResource();
    }
}
