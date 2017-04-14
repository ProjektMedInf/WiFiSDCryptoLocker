package io.github.projektmedinf.wifisdcryptolocker.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * Created by stiefel40k on 14.04.17.
 */
public class CryptoUtils {

    private static PasswordEncoder passwordEncoder = new SCryptPasswordEncoder();

    /**
     * Generates an Scrypt hash entry
     *
     * @param password The value which will be hashed
     * @return The hashed value
     */
    public static String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Compares if the hash value which was given by the user matches the one which has been stored in the database
     *
     * @param userInput     password which comes from the user
     * @param databaseInput value which comes from the database
     * @return true if the values match false otherwise
     */
    public static boolean comparePasswords(String userInput, String databaseInput) {
        return passwordEncoder.matches(userInput, databaseInput);
    }
}
