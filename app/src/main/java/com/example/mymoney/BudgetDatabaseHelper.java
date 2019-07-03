package com.example.mymoney;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BudgetDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Budget.db";
    public static final String TABLE_NAME = "BUDGET";
    public static final String COL_1 = "ID";
    public static final String COL_3 = "AMOUNT";

    public BudgetDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT INTEGER)" );

    }

    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(Integer month,Integer budget_amount){
        // set the format to sql date time
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
        ContentValues initialValues = new ContentValues();
        initialValues.put("ID", month+1);
        initialValues.put("AMOUNT",budget_amount);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME,null, initialValues);

        if (result == -1){
            return false;
        }
        else
            return true;
    }

    public Cursor getTransactions() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM BUDGET",null);
        return cursor;
    }

    public Integer deleteData(String month){
        int monthno;
        if (month.equals("January"))
            monthno = 0;
        else if (month.equals("February")){
            monthno = 1;
        }
        else if (month.equals("March")) {
            monthno = 2;
        }
        else if (month.equals("April")) {
            monthno = 3;
        }
        else if (month.equals("May")) {
            monthno = 4;
        }
        else if (month.equals("June")){
            monthno = 5;
        }
        else if(month.equals("July")){
            monthno = 6;
        } else if(month.equals("August")){
            monthno = 7;
        } else if(month.equals("September")){
            monthno = 8;
        } else if(month.equals("October")){
            monthno = 9;
        } else if(month.equals("November")){
            monthno = 10;
        } else{
            monthno = 11;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {Integer.toString(monthno+1)});
    }
}
