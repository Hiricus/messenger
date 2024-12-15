package com.pavmaxdav.mymess;

import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.entity.UserData;
import com.pavmaxdav.mymess.repository.UserRepository;
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

		//userRepository.save(createUserMe());
		//userService.removeByLogin("Hiricus");
		//userDataService.removeById(102);
	}

	public static User createUserMe() {
		User user = new User("Hiricus", "hexaeder@yandex.ru", "2556145");
		UserData userData = new UserData(user, "setting1, setting2, theme=dark", "Хто я?");
		user.setUserData(userData);
		return user;
	}
}
