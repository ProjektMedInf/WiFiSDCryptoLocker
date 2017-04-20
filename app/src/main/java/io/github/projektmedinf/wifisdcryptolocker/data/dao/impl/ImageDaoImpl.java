package io.github.projektmedinf.wifisdcryptolocker.data.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.ImageDao;
import io.github.projektmedinf.wifisdcryptolocker.model.Image;
import io.github.projektmedinf.wifisdcryptolocker.model.Session;
import io.github.projektmedinf.wifisdcryptolocker.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static io.github.projektmedinf.wifisdcryptolocker.data.model.ImageDatabaseModel.*;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class ImageDaoImpl implements ImageDao {

    private SQLiteDatabase sqLiteDatabase;

    public ImageDaoImpl(Context context) {
        sqLiteDatabase = new DatabaseHelper(context).getReadableDatabase();
    }

    @Override
    public List<Image> getImagesBySession(Session session) {
        List<Image> imageList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_IMAGE, null, "fk_session_id=?",
                new String[]{Long.toString(session.getId())}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                imageList.add(new Image(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_IMAGE_ID)),
                        cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_ENCRYPTED_IMAGE_DATA)),
                        cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_INITIALISATION_VECTOR)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_PADDING)),
                        session,
                        null)
                );

            }
            cursor.close();
        }

        return imageList;
    }
}
