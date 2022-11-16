package com.spring.dictionary;

import com.spring.dictionary.actors.Role;
import com.spring.dictionary.actors.User;
import com.spring.dictionary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DictionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DictionaryApplication.class, args);
	}



//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//			userService.saveRole(new Role(null, "ROLE_USER"));
//
//			userService.saveUser(new User(null, "John", "Smith", "john","password", new ArrayList<>()));
//			userService.saveUser(new User(null, "Maggie", "Stone", "magforever","password", new ArrayList<>()));
//
//			userService.addRoleToUser("john", "ROLE_ADMIN");
//			userService.addRoleToUser("magforever", "ROLE_USER");
//		};
//	}

}
