package com.example.uzachap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TownAdapter extends RecyclerView.Adapter<TownAdapter.MyViewHolder> {

    List<Town> towns;
    Context context;
    String town_name_value;
    int town_no_value;

    public TownAdapter(Context context, List<Town> towns) {
        this.towns = towns;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.town_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Town town = towns.get(position);
        holder.town_name.setText(town.getTownName());
        holder.town_no.setText(String.valueOf(town.getTownNo()));
    }

    @Override
    public int getItemCount() {
        return towns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView card_town;
        TextView town_name;
        TextView town_no;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_town = itemView.findViewById(R.id.card_town);
            town_name = itemView.findViewById(R.id.txtTownName);
            town_no = itemView.findViewById(R.id.txtTownNumber);
            card_town.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    town_name_value = town_name.getText().toString();
                    town_no_value = Integer.parseInt(town_no.getText().toString());

                    Intent mIntent = new Intent(context, SendMessageActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("TOWN_NAME", town_name_value);
                    mBundle.putInt("TOWN_NO", town_no_value);
                    mIntent.putExtras(mBundle);
                    context.startActivity(mIntent);
                }
            });
          }
    }
}
