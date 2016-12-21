package com.mohan.contactsmap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mohan on 6/10/16.
 */

public class Dbclass extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final int DB_VERSION = 160;
    private static final String DB_NAME = "contactsmap.db";


    public void initialize() {
        db = this.getWritableDatabase();
    }

    private static Dbclass dbInstance;

    public static synchronized Dbclass getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new Dbclass(context);
            dbInstance.initialize();
        }
        return dbInstance;
    }

    public Dbclass(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * TABLE FOR PREFILL RESPONSE
     */
    public static final String TABLE_CONTACTS = "contacts_table";
    public static final String CONTACT_ID = "_id";
    public static final String CONTACT_NAME = "contact_name";
    public static final String CONTACT_IMAGE_URI = "contact_image";
    public static final String CONTACT_NUMBERS = "contact_numbers";


    public static final String CREATE_CONTACTS_TABLE ="CREATE TABLE "
            + TABLE_CONTACTS + " ("
            + CONTACT_ID + " INTEGER PRIMARY KEY, "
            + CONTACT_NAME + " text, "
            + CONTACT_IMAGE_URI + " text,"
            + CONTACT_NUMBERS + " text);";
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXIST "+TABLE_CONTACTS);
    }
}
