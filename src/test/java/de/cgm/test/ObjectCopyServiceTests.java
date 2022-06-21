package de.cgm.test;

import com.google.gson.Gson;
import de.cgm.test.api.user.model.dto.UserOutcomingDto;
import de.cgm.test.api.user.model.entity.User;
import de.cgm.test.base.lang.util.IObjectCopyService;
import de.cgm.test.api.user.model.dto.UserIncomingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class ObjectCopyServiceTests {

	private final String UserID = "8c73f512-30c7-4506-8b0c-ba43e1d7ab2e";

	@Autowired
	IObjectCopyService objectCopyService;

	@Test
	void copyIncomingToEntityWorksWithObjectCopyService() {

		final UserIncomingDto incomingDto = new UserIncomingDto(UserID, "justALoginWith123", "justAPassword321", "John", "Smith");
		User deepCopy = objectCopyService.copyFromIncomingDtoToEntity(incomingDto, User.class);
		var gson = new Gson();
		assertEquals("entity and incomingDto should be equals", gson.toJson(deepCopy), gson.toJson(incomingDto));
	}

	@Test
	void copyEntityToOutcomingDtoWorksWithObjectCopyService() {

		final User user = new User(UserID, "justALoginWith123", "justAPassword321", "John", "Smith", null);

		var users = List.of(user);
		List<UserOutcomingDto> deepCopies = objectCopyService.copyFromEntitiesToOutcomingDtos(users, UserOutcomingDto.class);
		assertEquals("entity and incomingDto should be equals", deepCopies.size(), users.size());
	}
}
