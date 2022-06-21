package de.cgm.test.base.lang.util;

import org.springframework.lang.NonNull;

import java.security.NoSuchAlgorithmException;

public interface IPasswordHashService {
    @NonNull
    String generateMD5Hash(@NonNull String password) throws NoSuchAlgorithmException;

    boolean compareWithMD5Hash(@NonNull String cleanPassword, @NonNull String md5hashedPassword) throws NoSuchAlgorithmException;
}
