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


import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    ArrayList<FoodModel> foodList;
    Context context;


    public FoodAdapter(ArrayList<FoodModel> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.MyViewHolder holder, int position) {
        FoodModel foodModel = foodList.get(position);
        holder.food_txt.setText(foodModel.getNotes());
        holder.date_txt.setText(foodModel.getDate());
        int amount = foodModel.getAmount();
        holder.food_amount.setText(Integer.toString(amount));
        holder.bd_name_txt.setText(foodModel.getBd_name());

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView food_txt,date_txt,food_amount,bd_name_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            food_txt = itemView.findViewById(R.id.food_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            food_amount = itemView.findViewById(R.id.food_amount);
            bd_name_txt = itemView.findViewById(R.id.bd_name_txt);
        }

    }
}

