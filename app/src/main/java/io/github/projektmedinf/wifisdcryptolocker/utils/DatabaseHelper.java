package io.github.projektmedinf.wifisdcryptolocker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.github.projektmedinf.wifisdcryptolocker.model.Userdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by stiefel40k on 13.04.17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wifisdCryptoLocker";

    // Table names
    private static final String TABLE_USERDATA = "userdata";
    private static final String TABLE_IMAGE = "image";
    private static final String TABLE_SESSION = "session";

    // Common column names
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_CREATED_AT = "created_at";
    private static final String COLUMN_NAME_INITIALISATION_VECTOR = "initialisation_vector";
    private static final String COLUMN_NAME_PADDING = "padding";

    // TABLE_USERDATA Table - column names
    private static final String COLUMN_NAME_USERNAME = "username";
    private static final String COLUMN_NAME_PASSWORD = "password";

    // TABLE_IMAGE
    private static final String COLUMN_NAME_ENCRYPTED_IMAGE_DATA = "encrypted_image_data";
    private static final String COLUMN_NAME_FK_SESSION_ID = "fk_session_id";

    // TABLE_SESSION
    private static final String COLUMN_NAME_ENCRYPTED_DATE = "encrypted_date";
    private static final String COLUMN_NAME_LOCATION = "location";

    // Table create statements
    private static final String CREATE_USERDATA = "CREATE TABLE " + TABLE_USERDATA + " ("
            + COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME_USERNAME + " TEXT UNIQUE NOT NULL, "
            + COLUMN_NAME_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_NAME_CREATED_AT + " DATETIME)";

    private static final String CREATE_IMAGE = "CREATE TABLE " + TABLE_IMAGE + " ("
            + COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME_ENCRYPTED_IMAGE_DATA + " BLOB NOT NULL, "
            + COLUMN_NAME_INITIALISATION_VECTOR + " BLOB NOT NULL, "
            + COLUMN_NAME_PADDING + " INTEGER,"
            + COLUMN_NAME_FK_SESSION_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_NAME_FK_SESSION_ID + ") REFERENCES " + TABLE_SESSION + "(" + COLUMN_NAME_ID + "))";

    private static final String CREATE_SESSION = "CREATE TABLE " + TABLE_SESSION + " ("
            + COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME_ENCRYPTED_DATE + " BLOB NOT NULL, "
            + COLUMN_NAME_LOCATION + " TEXT, "
            + COLUMN_NAME_CREATED_AT + " DATETIME)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_USERDATA);
        sqLiteDatabase.execSQL(CREATE_SESSION);
        sqLiteDatabase.execSQL(CREATE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION);

        onCreate(sqLiteDatabase);
    }

    public Userdata getUserdataByName(String username) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Userdata toReturn = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Cursor cursor = db.query(TABLE_USERDATA, null, "username=?",
                new String[]{username}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                try {
                    toReturn = new Userdata(
                            cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USERNAME)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PASSWORD)),
                            dateFormat.parse(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CREATED_AT)))
                    );
                } catch (ParseException e) {
                    throw new SQLException("Parsing the date led to an error.");
                }
            }
        }

        return toReturn;
    }

    /**
     * Inserts a new user into the database
     *
     * @param username the chosen username
     * @param password the hashed password
     * @return the id of the user on success. -1 if a general database error occurred and -2 i the user already exists.
     */
    public long insertUserdata(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        if (getUserdataByName(username) != null) {
            return -2;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_USERNAME, username);
        contentValues.put(COLUMN_NAME_PASSWORD, password);
        contentValues.put(COLUMN_NAME_CREATED_AT, getDateTime());

        return db.insert(TABLE_USERDATA, null, contentValues);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
