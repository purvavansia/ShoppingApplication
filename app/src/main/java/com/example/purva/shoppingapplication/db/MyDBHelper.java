package com.example.purva.shoppingapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by purva on 4/4/18.
 */

public class MyDBHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "my_database";
    public static final String TABLE_NAME = "my_cart";
    public static final String MOBILE = "mobile";
    public static final String PID= "product_id";
    public static final String PNAME = "product_name";
    public static final String PRICE = "product_price";
    public static final String QUANTITY = "product_quantity";
    public static final String DESCRIPTION = "product_desc";
    public static final String Image = "product_image";
    public static final int VERSION = 2;



    public MyDBHelper(Context context) {
        super(context, DB_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_NAME + "("
                + MOBILE + " TEXT,"
                + PID + " TEXT,"
                + PNAME + " TEXT,"
                + PRICE + " TEXT,"
                + QUANTITY + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + Image + " TEXT"
                + ")";

        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(db);
    }
}
