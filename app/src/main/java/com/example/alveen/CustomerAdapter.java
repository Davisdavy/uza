package com.example.alveen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    List<Customer> mobileList;
    Context context;
    public CustomerAdapter(Context context, List<Customer> mobileList) {
        this.mobileList = mobileList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.mobile_list_item, parent, false);
        return new CustomerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Customer mobilelist = mobileList.get(position);
        holder.mobile_no.setText(mobilelist.getMobileNo());
    }

    @Override
    public int getItemCount() {
        return mobileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView card_town;
        TextView mobile_no;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_town = itemView.findViewById(R.id.card_town);
            mobile_no = itemView.findViewById(R.id.txtMobileNo);
        }
    }

}
