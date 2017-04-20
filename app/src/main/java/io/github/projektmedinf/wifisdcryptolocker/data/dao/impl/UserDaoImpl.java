package io.github.projektmedinf.wifisdcryptolocker.data.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.UserDao;
import io.github.projektmedinf.wifisdcryptolocker.model.User;
import io.github.projektmedinf.wifisdcryptolocker.utils.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static io.github.projektmedinf.wifisdcryptolocker.data.model.UserDatabaseModel.*;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class UserDaoImpl implements UserDao {



    private SQLiteDatabase sqLiteDatabase;

    public UserDaoImpl(Context context){
        sqLiteDatabase = new DatabaseHelper(context).getReadableDatabase();
    }

    @Override
    public User getUserByUserName(String userName) {
        User toReturn = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_USER, null, "username=?",
                new String[]{userName}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                try {
                    toReturn = new User(
                            cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_USER_ID)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USERNAME)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PASSWORD)),
                            dateFormat.parse(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CREATED_AT)))
                    );
                } catch (ParseException e) {
                    throw new SQLException("Parsing the date led to an error.");
                }
            }
            cursor.close();
        }

        return toReturn;
    }

    @Override
    public long insertUser(String userName, String password) {
        if (getUserByUserName(userName) != null) {
            return -2;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_USERNAME, userName);
        contentValues.put(COLUMN_NAME_PASSWORD, password);
        contentValues.put(COLUMN_NAME_CREATED_AT, getDateTime());

        return sqLiteDatabase.insert(TABLE_NAME_USER, null, contentValues);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
