package io.github.projektmedinf.wifisdcryptolocker.utils;

import edu.vt.middleware.password.*;
import io.github.projektmedinf.wifisdcryptolocker.exceptions.InvalidPasswordException;
import io.github.projektmedinf.wifisdcryptolocker.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

import static io.github.projektmedinf.wifisdcryptolocker.utils.Constansts.*;

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
     * @return true, if the password is ok, false otherwise
     * @throws InvalidPasswordException in case of invalid password
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

    /**
     * Handles the encryption and decryption of a given byte array
     *
     * @param toHandle the byte array which should be decrypted or encrypted
     * @param user     the user whom key should be used
     * @param mode     mode of operation (encryption = 1, decryption = 2)
     * @return the encrypted or decrypted byte array
     * @throws GeneralSecurityException in case of an error
     */
    public static byte[] handleByteArrayCrypto(byte[] toHandle, User user, int mode) throws GeneralSecurityException {

        byte[] derivedKey = deriveKey(user.getPassword().toCharArray(), user.getSalt(), KEY_LENGTH);
        SecretKey cryptoKey = decryptCryptoKey(derivedKey, user);
        Cipher cipher = Cipher.getInstance(AES_CTR_NO_PADDING, BOUNCY_CASTLE);
        cipher.init(mode, cryptoKey);
        return cipher.doFinal(toHandle);
    }

    /**
     * Derives a key from a given password and salt
     *
     * @param password the password which will be used for the derivation
     * @param salt     the salt which is needed for the derivation
     * @param keyLen   length of the derived key
     * @return the derived key as byte array
     * @throws GeneralSecurityException in case of an error
     */
    public static byte[] deriveKey(char[] password, byte[] salt, int keyLen) throws GeneralSecurityException {

        SecretKeyFactory kf = SecretKeyFactory.getInstance(PBKDF_2_WITH_HMAC_SHA_1, BOUNCY_CASTLE);
        KeySpec specs = new PBEKeySpec(password, salt, 1024, keyLen);
        SecretKey key = kf.generateSecret(specs);
        return key.getEncoded();
    }

    /**
     * Decrypts the cryptoKey of the user with the given derived key
     *
     * @param derivedKey derived key of the user
     * @param user       the user for which the cryptoKey should be decrypted
     * @return the decrypted cryptoKey
     * @throws GeneralSecurityException in case of an error
     */
    private static SecretKey decryptCryptoKey(byte[] derivedKey, User user) throws GeneralSecurityException {

        Cipher cipher = Cipher.getInstance(AES_CTR_NO_PADDING, BOUNCY_CASTLE);
        SecretKey derivedKeySecretKey = new SecretKeySpec(derivedKey, 0, derivedKey.length, AES);
        cipher.init(Cipher.DECRYPT_MODE, derivedKeySecretKey, new IvParameterSpec(user.getCryptoKeyIV()));
        byte[] encryptionKeyDecrypted = cipher.doFinal(user.getCryptoKey());

        return new SecretKeySpec(encryptionKeyDecrypted, 0, encryptionKeyDecrypted.length, AES);
    }
}
