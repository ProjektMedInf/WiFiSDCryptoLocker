package io.github.projektmedinf.wifisdcryptolocker.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static io.github.projektmedinf.wifisdcryptolocker.data.model.ImageDatabaseModel.CREATE_TABLE_IMAGE;
import static io.github.projektmedinf.wifisdcryptolocker.data.model.ImageDatabaseModel.TABLE_NAME_IMAGE;
import static io.github.projektmedinf.wifisdcryptolocker.data.model.SessionDatabaseModel.CREATE_TABLE_SESSION;
import static io.github.projektmedinf.wifisdcryptolocker.data.model.SessionDatabaseModel.TABLE_NAME_SESSION;
import static io.github.projektmedinf.wifisdcryptolocker.data.model.UserDatabaseModel.CREATE_TABLE_USER;
import static io.github.projektmedinf.wifisdcryptolocker.data.model.UserDatabaseModel.TABLE_NAME_USER;

/**
 * Created by stiefel40k on 13.04.17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wifisdCryptoLocker";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_SESSION);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_IMAGE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SESSION);

        onCreate(sqLiteDatabase);
    }
}
