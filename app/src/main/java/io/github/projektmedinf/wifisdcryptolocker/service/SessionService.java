package io.github.projektmedinf.wifisdcryptolocker.service;

import io.github.projektmedinf.wifisdcryptolocker.model.Session;

import java.util.List;

/**
 * Created by stiefel40k on 20.04.17.
 */
public interface SessionService {

    /**
     * Get all {@code Session}s
     *
     * @return list of all sessions
     */
    public List<Session> getAllSessions();

    /**
     * Insert a new {@code Session} given a {@code session}
     *
     * @param session session to insert
     * @return the id of the newly inserted session
     */
    long insertSession(Session session);
}
