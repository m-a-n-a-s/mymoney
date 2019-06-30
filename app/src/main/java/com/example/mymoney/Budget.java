package com.example.mymoney;

import android.widget.Button;

public class Budget {
    private String amount;
    private String month;

    private Button delete;

    public Budget(String amount, String month) {
        this.amount = amount;
        this.month = month;
    }

    public String getAmount() {
        return amount;
    }

    public String getMonth() {
        return month;
    }



    public Button budgetDelete() {
        return delete;
    }
}
