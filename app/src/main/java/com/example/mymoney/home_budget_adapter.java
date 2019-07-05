package com.example.mymoney;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class home_budget_adapter extends RecyclerView.Adapter<home_budget_adapter.ViewHolder> {

    BudgetDatabaseHelper bdHelper;

    DatabaseHelper databaseHelper;
    private ArrayList<home_budget> budget;
    Context context1;

    public home_budget_adapter (Context context, ArrayList<home_budget> list){
        context1 = context;
        bdHelper = new BudgetDatabaseHelper(context);
        databaseHelper = new DatabaseHelper(context);
        budget = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_budget;
        TextView tv_spend;
        TextView tv_spend_today;
        //TextView tv_start_date;
        //TextView tv_end_date;
        TextView tv_month;
        TextView tv_days_left;
        TextView tv_safe_spend;
        TextView textView9;
        CircularProgressBar progress_bar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_budget = itemView.findViewById(R.id.tv_budget);
            tv_spend = itemView.findViewById(R.id.tv_spend);
            tv_spend_today = itemView.findViewById(R.id.tv_spend_today);
            //tv_start_date = itemView.findViewById(R.id.tv_start_date);
            //tv_end_date = itemView.findViewById(R.id.tv_end_date);
            tv_month= itemView.findViewById(R.id.tv_month);
            tv_safe_spend = itemView.findViewById(R.id.tv_safe_spend);
            tv_days_left= itemView.findViewById(R.id.tv_days_left);
            progress_bar= itemView.findViewById(R.id.progress_bar);
            textView9 = itemView.findViewById(R.id.textView9);
        }
    }

    @NonNull
    @Override
    public home_budget_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_row_layout, parent, false);

        return new ViewHolder(view);
    }
    public static final String TAG = "MainActivity";

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull home_budget_adapter.ViewHolder holder, int position) {

        holder.itemView.setTag(budget.get(position));
        int spent_today = 0;

        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDate = cal.get(Calendar.DATE);
        Cursor cursor = (Cursor) databaseHelper.getTransactions();
        Cursor bdcursor = (Cursor) bdHelper.getTransactions();


        StringBuffer sb = new StringBuffer();
        Integer[] months = new Integer[13];
        for(int i=0;i<13;i++){
            months[i] = 0;
        }
        //month at 5 and 6
        while(cursor.moveToNext()) {
            sb.append(cursor.getString(1) + "---> " + cursor.getString(2) + "\n");
            int month = Integer.parseInt(""+sb.charAt(5) + sb.charAt(6));
            if(cursor.getString(2).charAt(0) == '-' )
            {
                months[month] += 0-Integer.parseInt(cursor.getString(2));
                months[12] = Integer.parseInt("" + sb.charAt(0) + sb.charAt(1) + sb.charAt(2) + sb.charAt(3));
                //Log.d(TAG,"\n\n\n"+sb.charAt(8)+sb.charAt(9)+"\n\n\n");
                //Log.d(TAG,"\n\n\n"+sb.charAt(5)+sb.charAt(6)+"\n\n\n");

                if(
                        (currentDate == Integer.parseInt(""+sb.charAt(8)+sb.charAt(9)))
                                &&
                                (currentMonth + 1 == Integer.parseInt(""+sb.charAt(5)+sb.charAt(6))))
                {
                    spent_today +=   0-Integer.parseInt(cursor.getString(2));

                }
            }
        }



        cal.set(cal.YEAR, cal.MONTH, currentDate);
        int maxDay = cal.getActualMaximum(cal.DAY_OF_MONTH);
        int daysLeft = maxDay-currentDate+1;
        Log.d("snla", "\n\n\n"+maxDay+"\n\n\n"+cal.DATE);


        ArrayList<String> arrayList = new ArrayList<String>();
        for(int s:months) {
            arrayList.add(String.valueOf(s));
        }
        StringBuffer budgetsb = new StringBuffer();


        int currMonth = Calendar.getInstance().get(Calendar.MONTH);
        Log.d("a","\n\n\n\nCurrent Month" + currMonth);

        int budget_for = 0, month_no = -1;
        while(bdcursor.moveToNext()) {
            month_no = Integer.parseInt(bdcursor.getString(0));
            if(month_no == currMonth+1) {
                budget_for = Integer.parseInt(bdcursor.getString(1));
                Log.d(TAG, "\n\n\n" + bdcursor.getString(0) + "\n\n\n");
                break;
            }
        }

        float percent = 0;
        if (budget_for != 0) {

            percent = ((float) months[currentMonth + 1] / budget_for) * 100;
        }

        holder.tv_budget.setText(" Budget : ₹ "+budget_for);
        holder.tv_spend.setText("Spent: ₹ "+months[currentMonth+1]);
        int safe_spend = budget_for - months[currentMonth+1];
        if(safe_spend>0) {
            holder.tv_safe_spend.setText("₹ "+safe_spend);
        }
        else{
            holder.tv_safe_spend.setText("₹ 0");
        }
        //holder.tv_spend_today.setText(budget.get(position).getSpend_today());
        holder.tv_spend_today.setText("Spent Today: ₹ "+spent_today);
        //holder.tv_start_date.setText("");
        //holder.tv_end_date.setText("");
        String monthString = new DateFormatSymbols().getMonths()[currentMonth];
        holder.tv_month.setText("Current month: "+ monthString);
        if(daysLeft==1)
            holder.tv_days_left.setText(daysLeft + " day left");
        else
            holder.tv_days_left.setText((daysLeft + " days left"));

        holder.progress_bar.setProgress(percent);

        if(percent >= 90 && budget_for != 0){
            MainActivity.getInstance().notify(position);
            Toast.makeText(context1 , "You have spent 90% of your planned budget this month", Toast.LENGTH_SHORT).show();
            holder.progress_bar.setColor(Color.parseColor("#F44336"));
            holder.progress_bar.setBackgroundColor(Color.parseColor("#FFCDD2"));
            holder.textView9.setTextColor(Color.parseColor("#F44336"));
        }
        else{
            holder.progress_bar.setColor(Color.parseColor("#4CAF50"));
            holder.progress_bar.setBackgroundColor(Color.parseColor("#C8E6C9"));
            holder.textView9.setTextColor(Color.parseColor("#4CAF50"));
        }


        Log.d("OIPOOK",""+percent);

    }

    @Override
    public int getItemCount() {
        return budget.size();
    }
}
