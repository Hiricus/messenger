package com.pavmaxdav.mymess;

import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.entity.UserData;
import com.pavmaxdav.mymess.repository.UserRepository;
import com.pavmaxdav.mymess.service.ChatService;
import com.pavmaxdav.mymess.service.UserDataService;
import com.pavmaxdav.mymess.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MymessApplication {

	public static void main(String[] args) {
		var context =  SpringApplication.run(MymessApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);
		UserService userService = context.getBean(UserService.class);
		UserDataService userDataService = context.getBean(UserDataService.class);
		ChatService chatService = context.getBean(ChatService.class);



		//userRepository.save(createUserMe());
		//userService.removeByLogin("Hiricus");
		//userDataService.removeById(102);

		//userRepository.save(createUserNotMe());

		//chatService.createEmptyChat("Личка типа", false);

//		chatService.addUserToChat(2, "Hiricus");
//		chatService.addUserToChat(2, "Bananaws");

//		chatService.removeUserFromChat(1, "Bananaws");

//		chatService.removeChat(2);
	}

	public static User createUserMe() {
		User user = new User("Hiricus", "hexaeder@yandex.ru", "2556145");
		UserData userData = new UserData(user, "setting1, setting2, theme=dark", "Хто я?");
		user.setUserData(userData);
		return user;
	}

	public static User createUserNotMe() {
		User user = new User("Bananaws", "banana@mail.ru", "123");
		UserData userData = new UserData(user, "setting0, setting3, theme=dark", "Инфа типа");
		user.setUserData(userData);
		return user;
	}
}
