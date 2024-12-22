package com.pavmaxdav.mymess.controller;

import com.pavmaxdav.mymess.dto.ChatDTO;
import com.pavmaxdav.mymess.dto.UserDTO;
import com.pavmaxdav.mymess.entity.Chat;
import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.mapper.ChatMapper;
import com.pavmaxdav.mymess.mapper.UserMapper;
import com.pavmaxdav.mymess.service.ChatService;
import com.pavmaxdav.mymess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class TestController {
    private final UserService userService;
    private final ChatService chatService;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;

    @Autowired
    public TestController(UserService userService, ChatService chatService, ChatMapper chatMapper, UserMapper userMapper) {
        this.userService = userService;
        this.chatService = chatService;
        this.chatMapper = chatMapper;
        this.userMapper = userMapper;
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
        List<Chat> chats = userService.getUsersChatsSorted(login);
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


    @GetMapping("/getMyLogin")
    public String getUserLogin(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }



    /// Прикидываю контроллеры на будущее
    // Инфа о себе
    @GetMapping("/api/profile")
    public ResponseEntity<Object> getOwnInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optionalUser = userService.findUserByLogin(userDetails.getUsername());

        // Если нет такого пользователя - возвращаем ответ 404
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        UserDTO userDTO = userMapper.toDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Инфа о любом пользователе
    @GetMapping("/api/users/byId/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        Optional<User> optionalUser = userService.findUserById(id);

        // Если нет такого пользователя - возвращаем ответ 404
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        UserDTO userDTO = userMapper.toDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/api/users/byLogin/{login}")
    public ResponseEntity<Object> getUserByLogin(@PathVariable String login) {
        Optional<User> optionalUser = userService.findUserByLogin(login);

        // Если нет такого пользователя - возвращаем ответ 404
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        UserDTO userDTO = userMapper.toDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
