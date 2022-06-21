package de.cgm.test;

import de.cgm.test.api.user.model.entity.User;
import de.cgm.test.api.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableConfigurationProperties
public class CGMTestApplication implements CommandLineRunner{

	final
	UserService userService;

	public CGMTestApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CGMTestApplication.class, args);
	}

	@Override
	public void run(String... args) {

		// just seed User to have at least one entry in collection
		var user = new User("98980123-b5c5-4c50-95da-0b7712652188", "login12345678", "F802EFED179A1774E783B5C716D076A6", "name", "surname", null);
		userService.save(user);

	}
}
