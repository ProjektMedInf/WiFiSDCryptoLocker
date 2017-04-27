package io.github.projektmedinf.wifisdcryptolocker.utils;

import edu.vt.middleware.password.*;
import io.github.projektmedinf.wifisdcryptolocker.exceptions.InvalidPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Checks if the given password satisfies the predefined password criteria
     *
     * @param password the password which should be checked
     * @throws InvalidPasswordException in case of invalid password
     * @return true, if the password is ok, false otherwise
     */
    public static boolean isPasswordValid(String password) throws InvalidPasswordException {

        // pwd between 8 and 16 chars
        LengthRule lengthRule = new LengthRule(8, 16);

        // no whitespaces
        WhitespaceRule whitespaceRule = new WhitespaceRule();

        CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();

        // at least one digit
        charRule.getRules().add(new DigitCharacterRule(1));

        //at least one non-alphabetic char
        charRule.getRules().add(new NonAlphanumericCharacterRule(1));

        // at least one upper case char
        charRule.getRules().add(new UppercaseCharacterRule(1));

        // at least one lower case char
        charRule.getRules().add(new LowercaseCharacterRule(1));

        charRule.setNumberOfCharacteristics(4);

        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(lengthRule);
        ruleList.add(whitespaceRule);
        ruleList.add(charRule);

        PasswordValidator passwordValidator = new PasswordValidator(ruleList);
        PasswordData passwordData = new PasswordData(new Password(password));

        System.out.println("Before validation");
        RuleResult ruleResult = passwordValidator.validate(passwordData);

        if (!ruleResult.isValid()) {
            StringBuilder error = new StringBuilder();
            for (String msg :
                    passwordValidator.getMessages(ruleResult)) {
                error.append(msg).append('\n');
            }
            throw new InvalidPasswordException(error.toString());

        }

        return ruleResult.isValid();
    }
}
