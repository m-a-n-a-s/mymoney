package com.example.mymoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class transaction_adapter extends RecyclerView.Adapter<transaction_adapter.ViewHolder> {

    private ArrayList<Transaction> transactions;

    public transaction_adapter (Context context, ArrayList<Transaction> list){
        transactions = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_info;
        TextView tv_date;
        TextView tv_expenditure;
        TextView tv_receive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_expenditure= itemView.findViewById(R.id.tv_expenditure);
            tv_receive= itemView.findViewById(R.id.tv_receive);

        }
    }

    @NonNull
    @Override
    public transaction_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_row_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull transaction_adapter.ViewHolder holder, int position) {
        holder.itemView.setTag(transactions.get(position));

        holder.tv_info.setText(transactions.get(position).getInfo());
        holder.tv_date.setText(transactions.get(position).getDate());
        holder.tv_receive.setText(transactions.get(position).getReceive());
        holder.tv_expenditure.setText(transactions.get(position).getExpenditure());

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
