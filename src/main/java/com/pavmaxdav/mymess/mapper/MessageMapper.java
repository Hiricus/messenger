package com.pavmaxdav.mymess.mapper;

import com.pavmaxdav.mymess.dto.CreateMessageDTO;
import com.pavmaxdav.mymess.dto.MessageDTO;
import com.pavmaxdav.mymess.entity.Chat;
import com.pavmaxdav.mymess.entity.Message;
import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.repository.ChatRepository;
import com.pavmaxdav.mymess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class MessageMapper {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public MessageMapper(UserRepository userRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public MessageDTO toDTO(Message message) {
        return new MessageDTO(message.getId(),
                message.getAuthor().getId(),
                message.getContent(),
                message.getSentAt());
    }

    @Transactional
    public Message toEntity(CreateMessageDTO createMessageDTO) {
        Optional<User> author = userRepository.findById(createMessageDTO.getUserId());
        Optional<Chat> chat = chatRepository.findById(createMessageDTO.getChatId());

        // Если автор или чат не найдены - возвращаем null
        if (author.isEmpty() || chat.isEmpty()) {
            return null;
        }

        // Возвращаем сообщение
        return new Message(createMessageDTO.getContent(), author.get(), chat.get());
    }
}
