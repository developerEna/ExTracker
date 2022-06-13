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

public class OthersAdapter extends RecyclerView.Adapter<OthersAdapter.MyViewHolder> {

    ArrayList<OthersModel> othersModels;
    Context context;


    public OthersAdapter(ArrayList<OthersModel> othersModels, Context context) {
        this.othersModels = othersModels;
        this.context = context;
    }

    @NonNull
    @Override
    public OthersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.others_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OthersAdapter.MyViewHolder holder, int position) {
        OthersModel othersModel = othersModels.get(position);
        holder.others_txt.setText(othersModel.getNotes());
        holder.date_txt.setText(othersModel.getDate());
        int amount = othersModel.getAmount();
        holder.others_amount.setText(Integer.toString(amount));
        holder.bd_name_txt.setText(othersModel.getBd_name());

    }

    @Override
    public int getItemCount() {
        return othersModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView others_txt,date_txt,others_amount,bd_name_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            others_txt = itemView.findViewById(R.id.others_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            others_amount = itemView.findViewById(R.id.others_amount);
            bd_name_txt = itemView.findViewById(R.id.bd_name_txt);
        }

    }
}

