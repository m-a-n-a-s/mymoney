package com.example.mymoney;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import android.widget.Toast;


public class set_budget extends AppCompatActivity {

    EditText et_budget;
    Button bt_submit;
    Spinner spinner_month;

    String budget_amount= "r";
    String month="r";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_budget);
        final BudgetDatabaseHelper bdHelper = new BudgetDatabaseHelper(this);


        et_budget = findViewById(R.id.et_budget);
        bt_submit = findViewById(R.id.bt_submit);
        spinner_month = (Spinner) findViewById(R.id.spinner_months);

        List<String> categories = new ArrayList<String>();
        categories.add("January");
        categories.add("February");
        categories.add("March");
        categories.add("April");
        categories.add("May");
        categories.add("June");
        categories.add("July");
        categories.add("August");
        categories.add("September");
        categories.add("October");
        categories.add("November");
        categories.add("December");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(dataAdapter);


        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_budget.getText().toString().isEmpty()){
                    Toast.makeText(set_budget.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    budget_amount = et_budget.getText().toString().trim();

                    month = String.valueOf(spinner_month.getSelectedItem());

                    int monthno=-1;
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
                    } else if(month.equals("December")){
                        monthno = 11;
                    }



                    boolean isInserted = bdHelper.insertData(monthno,Integer.parseInt(budget_amount));

                    if(isInserted) {
                        ApplicationClass.budget_list.add(new Budget(budget_amount, month));
                        frag_home.adapter.notifyItemChanged(0);
                        frag_notifiactions.myAdapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Budget already exists!",Toast.LENGTH_LONG).show();
                    }

                    set_budget.this.finish();
                }
            }
        });
    }
}
