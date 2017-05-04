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
                            cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_SALT)),
                            cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_CRYPTOKEY)),
                            cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_CRYPTOKEY_IV)),
                            dateFormat.parse(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CREATED_AT))),
                            cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PASSWORD))
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
    public long insertUser(User user) {
        if (getUserByUserName(user.getUsername()) != null) {
            return -2;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_USERNAME, user.getUsername());
        contentValues.put(COLUMN_NAME_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_NAME_SALT, user.getSalt());
        contentValues.put(COLUMN_NAME_CRYPTOKEY, user.getCryptoKey());
        contentValues.put(COLUMN_NAME_CRYPTOKEY_IV, user.getCryptoKeyIV());
        contentValues.put(COLUMN_NAME_CREATED_AT, getDateTime());

        return sqLiteDatabase.insert(TABLE_NAME_USER, null, contentValues);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
