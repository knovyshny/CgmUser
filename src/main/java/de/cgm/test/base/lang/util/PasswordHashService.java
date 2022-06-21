package de.cgm.test.base.lang.util;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * service is used to convert password to MD5 hash
 * and to check if clean password correspond to
 * hashed one
 * @author Kiryl Navyshny
 */
@Service
public class PasswordHashService implements IPasswordHashService {

    @Override
    @NonNull
    public String generateMD5Hash(@NonNull String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    @Override
    public boolean compareWithMD5Hash(@NonNull String cleanPassword, @NonNull String md5hashedPassword) throws NoSuchAlgorithmException {
        return Objects.equals(this.generateMD5Hash( cleanPassword ), md5hashedPassword);
    }

}
