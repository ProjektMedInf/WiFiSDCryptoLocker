package io.github.projektmedinf.wifisdcryptolocker.data.model;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class SessionDatabaseModel {

    public static final String TABLE_NAME_SESSION = "session";
    public static final String COLUMN_NAME_SESSION_ID = "id";
    public static final String COLUMN_NAME_ENCRYPTED_DATE = "encrypted_date";
    public static final String COLUMN_NAME_LOCATION = "location";
    public static final String COLUMN_NAME_INITIALISATION_VECTOR = "initialisation_vector";
    public static final String CREATE_TABLE_SESSION = "CREATE TABLE " + TABLE_NAME_SESSION + " ("
            + COLUMN_NAME_SESSION_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME_ENCRYPTED_DATE + " BLOB NOT NULL, "
            + COLUMN_NAME_INITIALISATION_VECTOR + " BLOB NOT NULL,"
            + COLUMN_NAME_LOCATION + " TEXT)";
}
