package com.example.mymoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

public class home_budget_adapter extends RecyclerView.Adapter<home_budget_adapter.ViewHolder> {

    private ArrayList<home_budget> budget;

    public home_budget_adapter (Context context, ArrayList<home_budget> list){
        budget = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_budget;
        TextView tv_spend;
        TextView tv_spend_today;
        TextView tv_start_date;
        TextView tv_end_date;
        TextView tv_days_left;
        CircularProgressBar progress_bar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_budget = itemView.findViewById(R.id.tv_budget);
            tv_spend = itemView.findViewById(R.id.tv_spend);
            tv_spend_today = itemView.findViewById(R.id.tv_spend_today);
            tv_start_date = itemView.findViewById(R.id.tv_start_date);
            tv_end_date = itemView.findViewById(R.id.tv_end_date);
            tv_days_left= itemView.findViewById(R.id.tv_days_left);
            progress_bar= itemView.findViewById(R.id.progress_bar);

        }
    }

    @NonNull
    @Override
    public home_budget_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_row_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_budget_adapter.ViewHolder holder, int position) {
        holder.itemView.setTag(budget.get(position));

        holder.tv_budget.setText(budget.get(position).getAmount());
        holder.tv_spend.setText(budget.get(position).getSpend());
        holder.tv_spend_today.setText(budget.get(position).getSpend_today());
        holder.tv_start_date.setText(budget.get(position).getStart_date());
        holder.tv_end_date.setText(budget.get(position).getEnd_date());
        holder.tv_days_left.setText(budget.get(position).getDays_left());


    }

    @Override
    public int getItemCount() {
        return budget.size();
    }
}
