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
import com.example.extracker.Model.TransportModel;
import com.example.extracker.Model.UtilitiesModel;


import java.util.ArrayList;

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.MyViewHolder> {

    ArrayList<TransportModel> transportList;
    Context context;


    public TransportAdapter(ArrayList<TransportModel> transportList, Context context) {
        this.transportList = transportList;
        this.context = context;
    }

    @NonNull
    @Override
    public TransportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transport_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransportAdapter.MyViewHolder holder, int position) {
        TransportModel transportModel = transportList.get(position);
        holder.transport_txt.setText(transportModel.getNotes());
        holder.date_txt.setText(transportModel.getDate());
        int amount = transportModel.getAmount();
        holder.transport_amount.setText(Integer.toString(amount));
        holder.bd_name_txt.setText(transportModel.getBd_name());

    }


    @Override
    public int getItemCount() {
        return transportList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView transport_txt,date_txt,transport_amount,bd_name_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            transport_txt = itemView.findViewById(R.id.transport_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            transport_amount = itemView.findViewById(R.id.transport_amount);
            bd_name_txt = itemView.findViewById(R.id.bd_name_txt);
        }

    }
}

