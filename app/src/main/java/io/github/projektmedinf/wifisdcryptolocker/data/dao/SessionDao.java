package io.github.projektmedinf.wifisdcryptolocker.data.dao;

import io.github.projektmedinf.wifisdcryptolocker.model.Session;

import java.util.List;

/**
 * Created by stiefel40k on 20.04.17.
 */
public interface SessionDao {

    public List<Session> getAllSessions();

    public Session getSessionBySessionId(long sessionId);

    public long insertSession(Session session);
}
