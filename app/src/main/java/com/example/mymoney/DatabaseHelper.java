package com.example.mymoney;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Transactions.db";
    public static final String TABLE_NAME = "DATA_TABLE";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATE_TIME";
    public static final String COL_3 = "AMOUNT";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE_TIME TEXT, AMOUNT INTEGER)" );

    }

    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(Integer amount){
        // set the format to sql date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        initialValues.put("DATE_TIME", dateFormat.format(date));
        initialValues.put("AMOUNT",amount);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME,null, initialValues);

        if (result == -1){
            return false;
        }
        else
            return true;
    }

    public Cursor getTransactions() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM DATA_TABLE",null);
        return cursor;
    }
}
