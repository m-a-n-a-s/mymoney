package com.example.mymoney;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ApplicationClass extends Application {
    int totalSpent = 10;
    public static String TAG = "ApplicationClass";

    public static ArrayList<Budget> budget_list;
    public static ArrayList<home_budget> home_budget_list;
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        Cursor cursor = (Cursor) databaseHelper.getTransactions();
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);

        StringBuffer sb = new StringBuffer();
        Integer[] months = new Integer[13];
        for (int i = 0; i < 13; i++) {
            months[i] = 0;
        }
        //month at 5 and 6
        while (cursor.moveToNext()) {
            sb.append(cursor.getString(1) + "---> " + cursor.getString(2) + "\n");
            int month = Integer.parseInt("" + sb.charAt(5) + sb.charAt(6));
            months[month] += Integer.parseInt(cursor.getString(2));
            months[12] = Integer.parseInt("" + sb.charAt(0) + sb.charAt(1) + sb.charAt(2) + sb.charAt(3));
        }

        Log.d(TAG, "Current Month   --- " + currentMonth);

        Log.d(TAG, sb.toString());

        ArrayList<String> arrayList = new ArrayList<String>();
        for (int s : months) {
            arrayList.add(String.valueOf(s));
        }

        totalSpent = months[currentMonth];


        budget_list = new ArrayList<Budget>();
        budget_list.add(new Budget("1000", "march"));

        home_budget_list = new ArrayList<home_budget>();
        home_budget_list.add(new home_budget("Budget:5000","Spent :" + Integer.toString(totalSpent), "Spend Today:500","1 June 2019","30 June 2019", "2 Days Left"));


    }
}
