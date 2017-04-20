package io.github.projektmedinf.wifisdcryptolocker.service.impl;

import android.content.Context;
import io.github.projektmedinf.wifisdcryptolocker.model.Userdata;
import io.github.projektmedinf.wifisdcryptolocker.service.UserService;
import io.github.projektmedinf.wifisdcryptolocker.utils.DatabaseHelper;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class UserServiceImpl implements UserService{

    private DatabaseHelper databaseHelper;

    public UserServiceImpl(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public Userdata getUserByUserName(String userName) {
        return databaseHelper.getUserdataByName(userName);
    }

    @Override
    public long insertUser(String userName, String password) {
        return databaseHelper.insertUserdata(userName, password);
    }
}
