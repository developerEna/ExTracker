package com.example.extracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.extracker.Model.ExpenseModel;


import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    ArrayList<ExpenseModel> expenseList;
    Context context;


    public ExpenseAdapter(ArrayList<ExpenseModel> expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {
        ExpenseModel expenseModel = expenseList.get(position);
        holder.expense_txt.setText(expenseModel.getNotes());
        holder.date_txt.setText(expenseModel.getDate());
        int amount = expenseModel.getAmount();
        holder.ex_amount.setText(Integer.toString(amount));
        holder.bd_name_txt.setText(expenseModel.getBd_name());

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView expense_txt,date_txt,ex_amount,bd_name_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_txt = itemView.findViewById(R.id.expense_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            ex_amount = itemView.findViewById(R.id.ex_amount);
            bd_name_txt = itemView.findViewById(R.id.bd_name_txt);
        }

    }
}

