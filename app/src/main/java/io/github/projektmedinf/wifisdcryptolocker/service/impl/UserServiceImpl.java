package io.github.projektmedinf.wifisdcryptolocker.service.impl;

import android.content.Context;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.UserDao;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.impl.UserDaoImpl;
import io.github.projektmedinf.wifisdcryptolocker.exceptions.ServiceException;
import io.github.projektmedinf.wifisdcryptolocker.model.User;
import io.github.projektmedinf.wifisdcryptolocker.service.UserService;
import io.github.projektmedinf.wifisdcryptolocker.utils.CryptoUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import static io.github.projektmedinf.wifisdcryptolocker.utils.Constansts.*;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(Context context) {
        userDao = new UserDaoImpl(context);
    }

    /**
     * @see UserService#getUserByUserName(String)
     */
    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    /**
     * @see UserService#insertUser(User)
     */
    @Override
    public long insertUser(User user) throws ServiceException {
        if (user.getUsername() == null || user.getPasswordNotHashed() == null || user.getPassword() == null) {
            throw new ServiceException("The username and the password must be set.");
        }
        try {
            long returnValue = userDao.insertUser(generateAndEncryptRandomCryptoKey(generateSalt(user)));
            switch ((int) returnValue) {
                case -1:
                    throw new ServiceException("An error occurred during accessing the database");
                case -2:
                    throw new ServiceException("The username already exists");
                default:
                    return returnValue;
            }
        } catch (GeneralSecurityException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Generates a random encrypted password for the given user
     *
     * @param user the user new user
     * @return the user with generated cryptoKey
     * @throws GeneralSecurityException
     */
    private User generateAndEncryptRandomCryptoKey(User user) throws GeneralSecurityException {

        byte[] derivedKey = CryptoUtils.deriveKey(user.getPasswordNotHashed().toCharArray(), user.getSalt(), 128);
        SecretKey derivedKeySecretKey = new SecretKeySpec(derivedKey, 0, derivedKey.length, AES);

        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES, BOUNCY_CASTLE);
        keyGenerator.init(128, new SecureRandom());

        byte[] encryptionKey = keyGenerator.generateKey().getEncoded();

        Cipher cipher = Cipher.getInstance(AES_CTR_NO_PADDING, BOUNCY_CASTLE);
        cipher.init(Cipher.ENCRYPT_MODE, derivedKeySecretKey);

        user.setCryptoKey(cipher.doFinal(encryptionKey));
        user.setCryptoKeyIV(cipher.getIV());

        return user;
    }

    /**
     * Generates salt for the user
     *
     * @param user the new user
     * @return user with generated salt
     */
    private User generateSalt(User user) {

        byte[] salt = new byte[128];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        user.setSalt(salt);

        return user;
    }

}
