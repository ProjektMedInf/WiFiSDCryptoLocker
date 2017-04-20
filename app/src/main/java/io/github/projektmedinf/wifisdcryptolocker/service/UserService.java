package io.github.projektmedinf.wifisdcryptolocker.service;

import io.github.projektmedinf.wifisdcryptolocker.model.User;

/**
 * Created by stiefel40k on 20.04.17.
 */
public interface UserService {

    User getUserByUserName(String userName);

    long insertUser(String userName, String password);
}
