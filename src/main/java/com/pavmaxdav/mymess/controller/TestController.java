package com.pavmaxdav.mymess.controller;

import com.pavmaxdav.mymess.dto.ChatDTO;
import com.pavmaxdav.mymess.entity.Chat;
import com.pavmaxdav.mymess.mapper.ChatMapper;
import com.pavmaxdav.mymess.service.ChatService;
import com.pavmaxdav.mymess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TestController {
    private final UserService userService;
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    @Autowired
    public TestController(ChatService chatService, ChatMapper chatMapper, UserService userService) {
        this.chatService = chatService;
        this.chatMapper = chatMapper;
        this.userService = userService;
    }

    @GetMapping("/getAChat/{chatId}")
    public ChatDTO getSomeChat(@PathVariable Integer chatId) {
        Optional<Chat> optionalChat = chatService.getChat(chatId);
        if (optionalChat.isEmpty()) {
            return null;
        }
        ChatDTO chatDTO = chatMapper.toDTO(optionalChat.get());
        return chatDTO;
    }

    @GetMapping("/getUsersChats/{login}")
    public List<ChatDTO> getUsersChats(@PathVariable String login) {
        List<Chat> chats = userService.getUsersChats(login);
        List<ChatDTO> chatDTOS = new ArrayList<>();

        for (Chat chat : chats) {
            chatDTOS.add(chatMapper.toDTO(chat));
        }

        return chatDTOS;
    }

    // For testing
    // TODO
    // change to getUserInfo
    @GetMapping("/getUserLogin/{login}")
    public String getUserLogin(@PathVariable String login) {
        return login;
    }
}
