package io.github.projektmedinf.wifisdcryptolocker.data.model;

import static io.github.projektmedinf.wifisdcryptolocker.data.model.SessionDatabaseModel.COLUMN_NAME_SESSION_ID;
import static io.github.projektmedinf.wifisdcryptolocker.data.model.SessionDatabaseModel.TABLE_NAME_SESSION;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class ImageDatabaseModel {

    public static final String TABLE_NAME_IMAGE = "image";
    public static final String COLUMN_NAME_IMAGE_ID = "id";
    public static final String COLUMN_NAME_ENCRYPTED_IMAGE_DATA = "encrypted_image_data";
    public static final String COLUMN_NAME_INITIALISATION_VECTOR = "initialisation_vector";
    public static final String COLUMN_NAME_PADDING = "padding";
    public static final String COLUMN_NAME_FK_SESSION_ID = "fk_session_id";
    public static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + TABLE_NAME_IMAGE + " ("
            + COLUMN_NAME_IMAGE_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME_ENCRYPTED_IMAGE_DATA + " BLOB NOT NULL, "
            + COLUMN_NAME_INITIALISATION_VECTOR + " BLOB NOT NULL, "
            + COLUMN_NAME_PADDING + " INTEGER,"
            + COLUMN_NAME_FK_SESSION_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_NAME_FK_SESSION_ID + ") REFERENCES " + TABLE_NAME_SESSION + "(" + COLUMN_NAME_SESSION_ID + "))";
}
