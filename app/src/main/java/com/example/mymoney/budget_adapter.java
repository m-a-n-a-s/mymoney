package com.example.mymoney;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class budget_adapter extends RecyclerView.Adapter<budget_adapter.ViewHolder> {

    private ArrayList<Budget> budget;
    BudgetDatabaseHelper bdHelper;

    public budget_adapter (Context context, ArrayList<Budget> list){
        bdHelper = new BudgetDatabaseHelper(context);
        budget = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvamount;
        TextView tvmonth;

        Button bt_delete_budget;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvamount= itemView.findViewById(R.id.tvamount);
            tvmonth = itemView.findViewById(R.id.tvmonth);
            bt_delete_budget= itemView.findViewById(R.id.bt_delete_budget);


//            bt_edit_budget.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    tvamount.setText("hello");
//                }
//            });

//            bt_delete_budget.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    tvamount.setText(null);
//                    tvmonth.setText(null);
//                }
//            });
        }
    }
    @NonNull
    @Override
    public budget_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_row_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final budget_adapter.ViewHolder holder, final int position) {
        holder.itemView.setTag(budget.get(position));

        holder.tvmonth.setText("Month: "+budget.get(position).getMonth());
        holder.tvamount.setText("Budget: ₹ "+budget.get(position).getAmount());

        holder.bt_delete_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int del = bdHelper.deleteData(budget.get(position).getMonth() );
                if (del != 0) {
                    budget.remove(position);
                    budget_adapter.this.notifyDataSetChanged();
                    frag_home.adapter.notifyItemChanged(0);
                    Log.d("asd","Deleted!!!");
                }
                else
                    Log.d("bhad","Cannot delete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return budget.size();
    }
}
