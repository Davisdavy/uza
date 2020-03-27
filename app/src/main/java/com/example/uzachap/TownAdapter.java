package com.example.uzachap;

import android.content.Context;
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

    public TownAdapter(List<Town> towns) {
        this.towns = towns;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.town_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Town town = towns.get(position);
        holder.town_name.setText(town.getTown_name());
    }

    @Override
    public int getItemCount() {
        return towns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView card_town;
        TextView town_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_town = itemView.findViewById(R.id.card_town);
            town_name = itemView.findViewById(R.id.txtTownName);
            card_town.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Common.selectedCategory = categories.get(getAdapterPosition());//Assign current category
//                    int cat_id = getAdapterPosition();
//                    SharedPreferences pref = context.getSharedPreferences("myCatId", MODE_PRIVATE); // 0 - for private mode
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putInt("categoryID", cat_id + 1); // Storing boolean - true/false
//                    editor.apply();
//                    editor.commit(); // commit changes
//                    Intent intent = new Intent(context, ExamInstructionsActivity.class);
//                    context.startActivity(intent);
                }
            });

        }
        }
}
