package io.github.projektmedinf.wifisdcryptolocker.data.model;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class UserDatabaseModel {
    public static final String TABLE_NAME_USER = "user";
    public static final String COLUMN_NAME_USER_ID = "id";
    public static final String COLUMN_NAME_USERNAME = "username";
    public static final String COLUMN_NAME_PASSWORD = "password";
    public static final String COLUMN_NAME_CRYPTOKEY = "cryptokey";
    public static final String COLUMN_NAME_SALT = "salt";
    public static final String COLUMN_NAME_CRYPTOKEY_IV = "cryptokey_iv";
    public static final String COLUMN_NAME_CREATED_AT = "created_at";
    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME_USER + " ("
            + COLUMN_NAME_USER_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME_USERNAME + " TEXT UNIQUE NOT NULL, "
            + COLUMN_NAME_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_NAME_CRYPTOKEY + " BLOB NOT NULL, "
            + COLUMN_NAME_SALT + " BLOB NOT NULL, "
            + COLUMN_NAME_CRYPTOKEY_IV + " BLOB NOT NULL, "
            + COLUMN_NAME_CREATED_AT + " DATETIME)";
}
