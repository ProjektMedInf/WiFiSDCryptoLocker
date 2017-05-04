package io.github.projektmedinf.wifisdcryptolocker.service;

import io.github.projektmedinf.wifisdcryptolocker.model.User;

/**
 * Created by stiefel40k on 20.04.17.
 *
 * Service layer for {@code User}.
 *
 * @see io.github.projektmedinf.wifisdcryptolocker.model.User
 */
public interface UserService {

    /**
     * Get the {@code User} by its {@code userName}
     *
     * @param userName name of the wanted user
     * @return the user matching the given user name
     * @throws illegal argument exception given null
     * @should return user matching the given user name
     * @should return null if no match was found
     * @should throw illegal argument exception given null
     */
    User getUserByUserName(String userName) throws IllegalArgumentException;

    /**
     * Insert a new {@code User} given an {@code userName} and a {@code password}
     *
     * @param userName name of the user
     * @param password password of the user
     * @return the id of the newly inserted user
     * @should create new user from given username and password
     * @should throw illegal argument exception given null
     */
    long insertUser(String userName, String password);
}
