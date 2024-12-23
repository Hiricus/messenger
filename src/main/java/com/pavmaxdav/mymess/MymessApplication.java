package com.pavmaxdav.mymess;

import com.pavmaxdav.mymess.entity.Message;
import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.entity.UserData;
import com.pavmaxdav.mymess.mapper.MessageMapper;
import com.pavmaxdav.mymess.mapper.UserMapper;
import com.pavmaxdav.mymess.repository.UserRepository;
import com.pavmaxdav.mymess.service.ChatService;
import com.pavmaxdav.mymess.service.UserDataService;
import com.pavmaxdav.mymess.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class MymessApplication {

	public static void main(String[] args) {
		var context =  SpringApplication.run(MymessApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);
		UserService userService = context.getBean(UserService.class);
		UserDataService userDataService = context.getBean(UserDataService.class);
		ChatService chatService = context.getBean(ChatService.class);
		MessageMapper messageMapper = context.getBean(MessageMapper.class);
		UserMapper userMapper = context.getBean(UserMapper.class);


//		userService.addUser(createUserMe());
//		userService.addUser(createUserNotMe());
//		userService.removeByLogin("Hiricus");

		//userDataService.removeById(102);


		chatService.createEmptyChat("Личка типа", false);
//		chatService.createEmptyChat("Групповая беседа типа", true);
//		chatService.createEmptyChat("Личка типа, но чужая", false);

//		chatService.addUserToChat(1, "Hiricus");
//		chatService.addUserToChat(1, "Bananaws");
//		chatService.addUserToChat(2, "Hiricus");
//		chatService.addUserToChat(2, "Bananaws");

//		chatService.removeUserFromChat(252, "Bananaws");

//		chatService.removeChat(52);

//		Optional<Message> optionalMessage = chatService.sendMessageToChat("Сообщение типа", "Hiricus", 1);
//		Optional<Message> optionalMessage = chatService.removeMessageFromChat(52, "Hiricus");
//		System.out.println(optionalMessage);

//		chatService.removeChat(1);

//		ArrayList<Message> messages = new ArrayList<>(chatService.getChat(1).get().getMessages());
//		System.out.println(messageMapper.toDTO(messages.get(0)));

//		User user = userService.findUserByLogin("Bananaws").get();
//		System.out.println(userMapper.toDTO(user));
	}

	public static User createUserMe() {
		User user = new User("Hiricus", "2556145");
		UserData userData = new UserData(user, "setting1, setting2, theme=dark", "Хто я?");
		user.setUserData(userData);
		return user;
	}

	public static User createUserNotMe() {
		User user = new User("Bananaws", "123");
		UserData userData = new UserData(user, "setting0, setting3, theme=dark", "Инфа типа");
		user.setUserData(userData);
		return user;
	}
}
