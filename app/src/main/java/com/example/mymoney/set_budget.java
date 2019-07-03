package com.example.mymoney;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class set_budget extends AppCompatActivity {

    EditText et_budget;
    //EditText et_month;
    Button bt_submit;
    Spinner spinner_month;

    //Fragment fragNotifications = new frag_notifiactions();
    //FragmentManager fragmentManager;


    String budget_amount= "r";
    String month="r";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_budget);

        et_budget = findViewById(R.id.et_budget);
        //et_month = findViewById(R.id.et_month);
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

//        fragmentManager = this.getSupportFragmentManager();
//        fragNotifications= (frag_notifiactions) fragmentManager.

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_budget.getText().toString().isEmpty()){//||et_month.getText().toString().isEmpty()){
                    Toast.makeText(set_budget.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    budget_amount = et_budget.getText().toString().trim();
                    //month = et_month.getText().toString().trim();
                    month = String.valueOf(spinner_month.getSelectedItem());

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
