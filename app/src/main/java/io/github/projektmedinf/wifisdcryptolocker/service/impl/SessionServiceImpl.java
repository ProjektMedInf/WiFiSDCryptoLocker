package io.github.projektmedinf.wifisdcryptolocker.service.impl;

import android.content.Context;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.SessionDao;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.impl.SessionDaoImpl;
import io.github.projektmedinf.wifisdcryptolocker.model.Session;
import io.github.projektmedinf.wifisdcryptolocker.service.SessionService;

import java.util.List;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class SessionServiceImpl implements SessionService{

    private SessionDao sessionDao;

    public SessionServiceImpl(Context context){
        sessionDao = new SessionDaoImpl(context);
    }

    @Override
    public List<Session> getAllSessions() {
        return sessionDao.getAllSessions();
    }
}
