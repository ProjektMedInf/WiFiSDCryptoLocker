package io.github.projektmedinf.wifisdcryptolocker.data.dao;

import io.github.projektmedinf.wifisdcryptolocker.model.User;

/**
 * Created by stiefel40k on 20.04.17.
 */
public interface UserDao {

    User getUserByUserName(String userName);

    /**
     * Inserts a new user into the database
     *
     * @param user user to insert
     * @return the id of the user on success. -1 if a general database error occurred and -2 i the user already exists.
     */
    long insertUser(User user);
}
