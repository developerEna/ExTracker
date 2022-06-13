package com.example.extracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.extracker.Model.FoodModel;
import com.example.extracker.Model.OthersModel;
import com.example.extracker.Model.UtilitiesModel;


import java.util.ArrayList;

public class UtilsAdapter extends RecyclerView.Adapter<UtilsAdapter.MyViewHolder> {

    ArrayList<UtilitiesModel> utilList;
    Context context;


    public UtilsAdapter(ArrayList<UtilitiesModel> utilList, Context context) {
        this.utilList = utilList;
        this.context = context;
    }

    @NonNull
    @Override
    public UtilsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.utility_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilsAdapter.MyViewHolder holder, int position) {
        UtilitiesModel utilitiesModel = utilList.get(position);
        holder.util_txt.setText(utilitiesModel.getNotes());
        holder.date_txt.setText(utilitiesModel.getDate());
        int amount = utilitiesModel.getAmount();
        holder.util_amount.setText(Integer.toString(amount));
        holder.bd_name_txt.setText(utilitiesModel.getBd_name());

    }

    @Override
    public int getItemCount() {
        return utilList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView util_txt,date_txt,util_amount,bd_name_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            util_txt = itemView.findViewById(R.id.utility_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            util_amount = itemView.findViewById(R.id.util_amount);
            bd_name_txt = itemView.findViewById(R.id.bd_name_txt);
        }

    }
}

