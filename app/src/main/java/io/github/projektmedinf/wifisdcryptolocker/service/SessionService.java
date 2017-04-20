package io.github.projektmedinf.wifisdcryptolocker.service;

import io.github.projektmedinf.wifisdcryptolocker.model.Session;

import java.util.List;

/**
 * Created by stiefel40k on 20.04.17.
 */
public interface SessionService {

    public List<Session> getAllSessions();
}
