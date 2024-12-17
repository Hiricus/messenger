package com.pavmaxdav.mymess.mapper;

import com.pavmaxdav.mymess.dto.ChatDTO;
import com.pavmaxdav.mymess.dto.UserDTO;
import com.pavmaxdav.mymess.entity.Chat;
import com.pavmaxdav.mymess.entity.Message;
import com.pavmaxdav.mymess.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Component
public class ChatMapper {
    private final UserMapper userMapper;
    private final MessageMapper messageMapper;

    @Autowired
    public ChatMapper(UserMapper userMapper, MessageMapper messageMapper) {
        this.userMapper = userMapper;
        this.messageMapper = messageMapper;
    }


    public ChatDTO toDTO(Chat chat) {
        ChatDTO chatDTO = new ChatDTO(chat.getId(),
                chat.getName(),
                chat.isGroup(),
                chat.getLastActive());

        // Создаём массив DTO пользователей участников чата
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        for (User user : chat.getUsers()) {
            userDTOS.add(userMapper.toDTO(user));
        }
        chatDTO.setUserDTOS(userDTOS);

        // Получаем последнее сообщение
        ArrayList<Message> messages = new ArrayList<>(chat.getMessages());
        if (messages.isEmpty()) {
            return chatDTO;
        } else {
            Message lastMessage = Collections.max(messages, Comparator.comparing(Message::getSentAt));
            // Добавляем последнее сообщение в объект
            chatDTO.setLastMessage(messageMapper.toDTO(lastMessage));

            return chatDTO;
        }
    }
}
