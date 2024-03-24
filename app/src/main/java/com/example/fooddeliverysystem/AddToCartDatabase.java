package com.example.fooddeliverysystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AddToCartDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "addtocart.db";
    private static final int DATABASE_VERSION = 7;
    private Context context;

    // Table and column names
    public static final String TABLE_NAME = "addproducts";
    public static final String COLUMN_ID = "_id";

    public static final String prdoctname = "prdoctname";
    public static final String img = "img";
    public static final String price = "price";
    public static final String custommerid = "custommerid";
    public static final String sellerid = "sellerid";
    public static final String product_id = "product_id";
    public static final String totalitem = "totalitem";

    // Create table statement


    public AddToCartDatabase(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + prdoctname + " TEXT NOT NULL, "
                + img + " TEXT NOT NULL , "
                +  " price TEXT NOT NULL, "
                + product_id + " TEXT NOT NULL, "
                + custommerid + " TEXT NOT NULL , "
                + totalitem + " TEXT NOT NULL , "
                + sellerid + " TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
    }

    public boolean insert(String prdoctname, String img, String price, String custommerid, String sellerid, String product_id,String totalitem) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("prdoctname", prdoctname);
        contentValues.put("img", img);
        contentValues.put("price", price);
        contentValues.put("custommerid", custommerid);
        contentValues.put("sellerid", sellerid);
        contentValues.put("totalitem", totalitem);
        contentValues.put("product_id", product_id);

        long result = database.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {

            return true;
        }
    }


    public Cursor getdata() {

        SQLiteDatabase database = this.getReadableDatabase();
         Cursor cursor = database.rawQuery("select * from "+TABLE_NAME,null);
          return  cursor;
    }


}
