package com.example.extracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.extracker.Model.BudgetModel;
import com.example.extracker.Model.ExpenseModel;

import java.util.ArrayList;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {
    ArrayList<BudgetModel> budgetList;
    Context context;


    public BudgetAdapter(ArrayList<BudgetModel> budgetList, Context context) {
        this.budgetList = budgetList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.budget_card,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BudgetModel budgetModel = budgetList.get(position);
        holder.in_txt.setText(budgetModel.getBd_name());
        int amount = budgetModel.getAmount();
        holder.in_amount.setText(Integer.toString(amount));
        holder.in_date.setText(budgetModel.getFromDate());

    }

    @Override
    public int getItemCount() {
        return budgetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView in_txt,in_date,in_amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            in_txt = itemView.findViewById(R.id.in_txt);
            in_date = itemView.findViewById(R.id.in_date);
            in_amount = itemView.findViewById(R.id.in_amount);
        }
    }
}
