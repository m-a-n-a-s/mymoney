package com.example.mymoney;

import android.app.Application;

import java.util.ArrayList;

public class ApplicationClass extends Application {

    public static ArrayList<Budget> budget_list;
    public static ArrayList<home_budget> home_budget_list;
    @Override
    public void onCreate() {
        super.onCreate();

        budget_list = new ArrayList<Budget>();
        budget_list.add(new Budget("1000", "march"));

        home_budget_list = new ArrayList<home_budget>();
        home_budget_list.add(new home_budget("Budget:5000","Spend:3000", "Spend Today:500","1 June 2019","30 June 2019", "2 Days Left"));


    }
}
