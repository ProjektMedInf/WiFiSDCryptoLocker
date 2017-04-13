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

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // TABLE_USERDATA Table - column names
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    // Table create statements
    private static final String CREATE_USERDATA = "CREATE TABLE " + TABLE_USERDATA + " ("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USERNAME + " TEXT UNIQUE NOT NULL, "
            + KEY_PASSWORD + " TEXT NOT NULL, "
            + KEY_CREATED_AT + " DATETIME)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USERDATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);

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
                            cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_USERNAME)),
                            cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)),
                            dateFormat.parse(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)))
                    );
                } catch (ParseException e) {
                    throw new SQLException("Parsing the date led to an error.");
                }
            }
        }

        return toReturn;
    }

    public long insertUserdata(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);
        contentValues.put(KEY_CREATED_AT, getDateTime());

        return db.insert(TABLE_USERDATA, null, contentValues);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
