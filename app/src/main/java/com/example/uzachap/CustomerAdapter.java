package com.example.uzachap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    List<Town> mobile;

    public CustomerAdapter(List<Town> mobile) {
        this.mobile = mobile;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_item, parent, false);
        return new CustomerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder holder, int position) {
        Town town = mobile.get(position);
        holder.mobile.setText(town.getMobile());
    }

    @Override
    public int getItemCount() {
        return mobile.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView card_customer;
        TextView mobile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_customer = itemView.findViewById(R.id.card_customer);
            mobile = itemView.findViewById(R.id.customerNumber);


        }
    }
}
