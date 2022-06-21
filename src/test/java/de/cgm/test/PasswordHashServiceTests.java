package de.cgm.test;

import de.cgm.test.base.lang.util.IPasswordHashService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class PasswordHashServiceTests {

	@Autowired
	IPasswordHashService passwordHashService;

	@Test
	void passwordHashAndComparisonWorks() throws NoSuchAlgorithmException {

		final String password = "MyFavoritPassword1234";

		var hashedPassword = passwordHashService.generateMD5Hash(password);

		assertTrue( "hashed password should not be null", hashedPassword != null );

		assertTrue("password was proper decrypted", passwordHashService.compareWithMD5Hash(password, hashedPassword));
	}

}
