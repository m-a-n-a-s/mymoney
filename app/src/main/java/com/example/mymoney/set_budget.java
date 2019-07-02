package com.example.mymoney;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class set_budget extends AppCompatActivity {

    EditText et_budget;
    EditText et_month;
    Button bt_submit;

    //Fragment fragNotifications = new frag_notifiactions();
    //FragmentManager fragmentManager;


    String budget_amount= "r";
    String month="r";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_budget);
        final BudgetDatabaseHelper bdHelper = new BudgetDatabaseHelper(this);


        et_budget = findViewById(R.id.et_budget);
        et_month = findViewById(R.id.et_month);
        bt_submit = findViewById(R.id.bt_submit);

//        fragmentManager = this.getSupportFragmentManager();
//        fragNotifications= (frag_notifiactions) fragmentManager.

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_budget.getText().toString().isEmpty()||et_month.getText().toString().isEmpty()){
                    Toast.makeText(set_budget.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    budget_amount = et_budget.getText().toString().trim();
                    month = et_month.getText().toString().trim();
                    int monthno=-1;
                    //month = month.toUpperCase();
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

                    if(isInserted){
                        Log.d("EFFDFDGFGFGHg","Data Inserted");
                    }

                    ApplicationClass.budget_list.add(new Budget(budget_amount, month));

                    frag_notifiactions.myAdapter.notifyDataSetChanged();

                    //frag_notifiactions.budget_list.
//                    Bundle bundle = new Bundle();
//                    bundle.putString("budget_amount", budget_amount);
//                    //bundle.putString("month", month);
//                    Fragment myobj = new frag_notifiactions();
//                    myobj.setArguments(bundle);
//                    Intent intent = new Intent();
//                    intent.putExtra("budget_amount", budget_amount);
//                    intent.putExtra("month", month);
//                    setResult(RESULT_OK,intent);
                    set_budget.this.finish();
                }
            }
        });
    }
//    public String[] getMyData(){
//        String[] data = new String[2];
//        data[0]= budget_amount;
//        data[1]= month;
//        return data;
//    }
}
