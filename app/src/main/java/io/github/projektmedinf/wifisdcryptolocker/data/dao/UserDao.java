package io.github.projektmedinf.wifisdcryptolocker.data.dao;

import io.github.projektmedinf.wifisdcryptolocker.model.Userdata;

/**
 * Created by stiefel40k on 20.04.17.
 */
public interface UserDao {

    Userdata getUserByUserName(String userName);

    /**
     * Inserts a new user into the database
     *
     * @param userName the chosen username
     * @param password the hashed password
     * @return the id of the user on success. -1 if a general database error occurred and -2 i the user already exists.
     */
    long insertUser(String userName, String password);
}
