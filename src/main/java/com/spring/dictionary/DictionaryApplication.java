package com.spring.dictionary;

import com.spring.dictionary.actors.Role;
import com.spring.dictionary.actors.User;
import com.spring.dictionary.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "WordEra Dictionary API", version = "1.0.0"),
                   servers = {@Server(url = "http://localhost:8080")},
                   tags={@Tag(name="Dictionary operations", description="These are Dictionary API endpoints to retrieve," +
						   " update and delete information from Dictionary database.")})

@SecurityScheme(name="BearerJWT", type= SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT",
				description = "Authenticate using JWT bearer tokens")
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
