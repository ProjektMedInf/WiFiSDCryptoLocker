package io.github.projektmedinf.wifisdcryptolocker.data.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.SessionDao;
import io.github.projektmedinf.wifisdcryptolocker.model.Session;
import io.github.projektmedinf.wifisdcryptolocker.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.github.projektmedinf.wifisdcryptolocker.data.model.SessionDatabaseModel.*;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class SessionDaoImpl implements SessionDao {

    private SQLiteDatabase sqLiteDatabase;

    public SessionDaoImpl(Context context) {
        sqLiteDatabase = new DatabaseHelper(context).getReadableDatabase();
    }

    @Override
    public List<Session> getAllSessions() {
        List<Session> sessionList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_SESSION, null, null,
                null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // TODO change date to decrypted
                sessionList.add(new Session(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_SESSION_ID)),
                        cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_ENCRYPTED_DATE)),
                        new Date(),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LOCATION)),
                        cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_INITIALISATION_VECTOR)))
                );

            }
            cursor.close();
        }

        return sessionList;
    }

    @Override
    public Session getSessionBySessionId(long sessionId) {
        Session toReturn = null;

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_SESSION, null, "id=?",
                new String[]{Long.toString(sessionId)}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                toReturn = new Session(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_SESSION_ID)),
                        cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_ENCRYPTED_DATE)),
                        new Date(),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LOCATION)),
                        cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_INITIALISATION_VECTOR))
                );
            }
            cursor.close();
        }

        return toReturn;
    }
}
