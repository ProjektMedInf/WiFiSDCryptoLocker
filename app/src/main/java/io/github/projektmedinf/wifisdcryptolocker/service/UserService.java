package io.github.projektmedinf.wifisdcryptolocker.service;

import io.github.projektmedinf.wifisdcryptolocker.model.Userdata;

/**
 * Created by stiefel40k on 20.04.17.
 */
public interface UserService {

    Userdata getUserByUserName(String userName);

    long insertUser(String userName, String password);
}
