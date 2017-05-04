package io.github.projektmedinf.wifisdcryptolocker.data.dao;

import io.github.projektmedinf.wifisdcryptolocker.model.User;

/**
 * Created by stiefel40k on 20.04.17.
 * <p>
 * Data layer for {@code User}.
 *
 * @see io.github.projektmedinf.wifisdcryptolocker.model.User
 */
public interface UserDao {

    /**
     * Get the {@code User} by its {@code userName}
     *
     * @param userName name of the wanted user
     * @return the user matching the given user name
     * @should return user matching the given user name
     * @should return null if no match was found
     */
    User getUserByUserName(String userName);

    /**
     * Inserts a new user into the database
     *
     * @param userName the chosen username
     * @param password the hashed password
     * @return the id of the user on success
     * @should return the id of the user on success
     * @should return -1 if a general database error occurs
     * @should return -2 if the user already exists
     */
    long insertUser(String userName, String password);
}
