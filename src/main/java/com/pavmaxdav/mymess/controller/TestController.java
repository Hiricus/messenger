package com.pavmaxdav.mymess.controller;

import com.pavmaxdav.mymess.dto.*;
import com.pavmaxdav.mymess.entity.*;
import com.pavmaxdav.mymess.entity.attached.Settings;
import com.pavmaxdav.mymess.entity.attached.Theme;
import com.pavmaxdav.mymess.mapper.ChatMapper;
import com.pavmaxdav.mymess.mapper.UserMapper;
import com.pavmaxdav.mymess.service.ChatService;
import com.pavmaxdav.mymess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TestController {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;
    private final ChatService chatService;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;

    @Autowired
    public TestController(UserService userService, ChatService chatService, ChatMapper chatMapper, UserMapper userMapper, SimpMessagingTemplate messagingTemplate) {
        this.userService = userService;
        this.chatService = chatService;
        this.chatMapper = chatMapper;
        this.userMapper = userMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/test/triggerSocket")
    public void triggerSocket() {
        messagingTemplate.convertAndSend("/chatUpdates", "hello chat");
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
        UserDTO userDTO = userMapper.toDTO(user, true);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Инфа о любом пользователе по id
    @GetMapping("/api/users/byId/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        Optional<User> optionalUser = userService.findUserById(id);

        // Если нет такого пользователя - возвращаем ответ 404
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        // Зануляем настройки чтоб не спалить чужие данные
        UserDTO userDTO = userMapper.toDTO(user, false);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Инфа о любом пользователе по логину
    @GetMapping("/api/users/byLogin/{login}")
    public ResponseEntity<Object> getUserByLogin(@PathVariable String login) {
        Optional<User> optionalUser = userService.findUserByLogin(login);

        // Если нет такого пользователя - возвращаем ответ 404
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        // Зануляем настройки чтоб не спалить чужие данные
        UserDTO userDTO = userMapper.toDTO(user, false);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Изменить аватарку
    @PostMapping("/api/profile/setAvatar")
    public ResponseEntity<Object> setAvatar(@RequestBody AttachedResourceDTO avatarDTO, @AuthenticationPrincipal UserDetails userDetails) {
        // Если не нашли пользователя - 404
        Optional<User> optionalUser = userService.findUserByLogin(userDetails.getUsername());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        Optional<UserData> optionalUserData = userService.getUserData(userDetails.getUsername());
        if (optionalUserData.isEmpty()) {
            userService.updateUserData(userDetails.getUsername(), new UserData("", "", avatarDTO.getResourceByte()));
        } else {
            userService.updateUserData(userDetails.getUsername(), new UserData(optionalUserData.get().getSettings(), optionalUserData.get().getAboutMe(), avatarDTO.getResourceByte()));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Получить все свои чаты
    @GetMapping("/api/chats/getAll")
    public ResponseEntity<Object> getUsersChatsSorted(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> optionalUser = userService.findUserByLogin(userDetails.getUsername());
        // Если не нашли юзера - кидаем 404
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Находим пользователя, все его чаты, и добавляем их в ответ
        User user = optionalUser.get();
        List<Chat> chats = userService.getUsersChatsSorted(user.getLogin());
        List<ChatDTO> chatDTOS = new ArrayList<>();
        for (Chat usersChat : chats) {
            chatDTOS.add(chatMapper.toDTO(usersChat));
        }
        return new ResponseEntity<>(chatDTOS, HttpStatus.OK);
    }

    // Получить инфу об одном чате
    @GetMapping("/api/chats/get/{id}")
    public ResponseEntity<Object> getChat(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Chat> optionalChat = chatService.getChat(id);

        // Если не нашли чат - 404
        if (optionalChat.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userService.findUserByLogin(userDetails.getUsername()).get();
        ArrayList<Chat> userChats = new ArrayList<>(user.getChats());
        boolean isUserInChat = false;
        for (Chat chat : userChats) {
            if (chat.getId().equals(id)) {
                isUserInChat = true;
                break;
            }
        }
        // Если пользователя нет в чате кидаем 406
        if (!isUserInChat) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        BigChatDTO chatDTO = chatMapper.toBigChatDTO(optionalChat.get());
        return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }

    // Создание нового чата
    @PostMapping("/api/chats/create")
    public ResponseEntity<Object> createNewChat(@RequestBody CreateChatDTO createChatDTO) {
        // Создаём и сохраняем пустой чат
//        System.out.println(createChatDTO);
        Optional<Chat> optionalChat = chatService.createEmptyChat(createChatDTO.getChatName(), createChatDTO.isGroup());
        if (optionalChat.isEmpty()) {
            System.out.println("Can't find chat");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Chat chat = optionalChat.get();
        // Добавляем пользователей в чат. Если не смогли добавить - возвращаем BAD REQUEST
        for (String login : createChatDTO.getUserList()) {
            Optional<User> user = chatService.addUserToChat(chat.getId(), login);
            if (user.isEmpty()) {
                System.out.println("Can't find user: " + login);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        // Получаем добавленный чат
        Optional<Chat> addedChat = chatService.getChat(chat.getId());

        // Рассылка всем пользователям
        for (User user : addedChat.get().getUsers()) {
            // Получаем список чатов пользователя
            List<Chat> userChats = userService.getUsersChatsSorted(user.getLogin());

            // Делаем список дто чатов
            List<ChatDTO> chatDTOS = new ArrayList<>();
            for (Chat usersChat : userChats) {
                chatDTOS.add(chatMapper.toDTO(usersChat));
            }
            // Рассылаем всем пользователям
            messagingTemplate.convertAndSendToUser(user.getLogin(), "/chatListUpdates", chatDTOS);
        }

        // Отдаём добавленный чат
        ChatDTO addedChatDTO = chatMapper.toDTO(addedChat.get());
        return new ResponseEntity<>(addedChatDTO, HttpStatus.CREATED);
    }

    // Отправка сообщения в чат
    @PostMapping("/api/chats/sendMessage")
    public ResponseEntity<Object> sendMessage(@RequestBody CreateMessageDTO createMessageDTO) {
//        System.out.println("trying to post new message: " + createMessageDTO);
        messagingTemplate.convertAndSend("/chatUpdates", "hello chat");

        Optional<User> optionalUser = userService.findUserById(createMessageDTO.getUserId());
        // Если автор не найден - 404
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User author = optionalUser.get();

        // Создаём ресурс из дто, если он в нём есть
        AttachedResource attachedResource = null;
        if (createMessageDTO.getResource() != null) {
            attachedResource = new AttachedResource(createMessageDTO.getResourceName(),
                    Base64.getDecoder().decode(createMessageDTO.getResource()),
                    createMessageDTO.getResourceType());
        }

        // Создаём и постим сообщение
        Optional<Message> message = chatService.sendMessageToChat(createMessageDTO.getContent(),
                author.getLogin(),
                createMessageDTO.getChatId(),
                attachedResource);

        // Если создали - Created, если нет Bad Request
        if (message.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Chat chat = chatService.getChat(createMessageDTO.getChatId()).get();

            BigChatDTO fullChatDTO = chatMapper.toBigChatDTO(chat);

            // Попытка глобальной рассылки
            messagingTemplate.convertAndSend("/chatUpdates/" + chat.getId(), fullChatDTO);
            System.out.println("Sending to chat: " + chat.getId());
            for (User user : chat.getUsers()) {
//                messagingTemplate.convertAndSendToUser(user.getLogin(), "/chatUpdates", fullChatDTO);


                List<Chat> chats = userService.getUsersChatsSorted(user.getLogin());
                List<ChatDTO> chatDTOS = new ArrayList<>();
                for (Chat usersChat : chats) {
                    chatDTOS.add(chatMapper.toDTO(usersChat));
                }
                messagingTemplate.convertAndSendToUser(user.getLogin(), "/chatListUpdates", chatDTOS);
                System.out.println("Sending to user: " + user.getLogin());
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    // Удаление сообщения из чата
    @DeleteMapping("/api/chats/removeMessage")
    public ResponseEntity<Object> removeMessage(@RequestParam(required = true) Integer messageId, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Message> removedMessage = chatService.removeMessageFromChat(messageId, userDetails.getUsername());

        if (removedMessage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
