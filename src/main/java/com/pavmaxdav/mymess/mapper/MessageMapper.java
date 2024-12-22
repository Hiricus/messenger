package com.pavmaxdav.mymess.mapper;

import com.pavmaxdav.mymess.dto.AttachedResourceDTO;
import com.pavmaxdav.mymess.dto.CreateMessageDTO;
import com.pavmaxdav.mymess.dto.MessageDTO;
import com.pavmaxdav.mymess.entity.AttachedResource;
import com.pavmaxdav.mymess.entity.Chat;
import com.pavmaxdav.mymess.entity.Message;
import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.repository.ChatRepository;
import com.pavmaxdav.mymess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
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

    @Transactional
    public MessageDTO toDTO(Message message) {
        // Если прикреплённого ресурса нет - создаём обычное дто сообщения
        if (message.getAttachedResource() == null) {
            return new MessageDTO(message.getId(),
                    message.getAuthor().getId(),
                    message.getContent(),
                    message.getSentAt());
        } else {  // Если есть прикреплённый ресурс - делаем дто сообщения с ним
            // Создаём дто сообщения
            MessageDTO messageDTO = new MessageDTO(message.getId(),
                    message.getAuthor().getId(),
                    message.getContent(),
                    message.getSentAt());

            // Создаём дто ресурса
            AttachedResourceDTO resourceDTO = new AttachedResourceDTO(
                    message.getAttachedResource().getName(),  // Название картинки или файла
                    Base64.getEncoder().encodeToString(message.getAttachedResource().getResource()),  // Из массива байт в строку
                    message.getAttachedResource().getResourceType());  // Тип ресурса

            // Прикрепляем ресурс к сообщению и возвращаем
            messageDTO.setAttachedResource(resourceDTO);
            return messageDTO;
        }
    }

    @Transactional
    public Message toEntity(CreateMessageDTO createMessageDTO) {
        Optional<User> author = userRepository.findById(createMessageDTO.getUserId());
        Optional<Chat> chat = chatRepository.findById(createMessageDTO.getChatId());

        // Если автор или чат не найдены - возвращаем null
        if (author.isEmpty() || chat.isEmpty()) {
            return null;
        }

        if (createMessageDTO.getResource() == null) {
            // Возвращаем обычное сообщение
            return new Message(createMessageDTO.getContent(), author.get(), chat.get());
        } else {
            // Создаём объект ресурса
            AttachedResource attachedResource = new AttachedResource(
                    createMessageDTO.getResourceName(),
                    Base64.getDecoder().decode(createMessageDTO.getResource()),
                    createMessageDTO.getResourceType());

            // Возвращаем объект сообщения с прикреплённым к нему ресурсом
            return new Message(createMessageDTO.getContent(), author.get(), chat.get(), attachedResource);
        }
    }
}
