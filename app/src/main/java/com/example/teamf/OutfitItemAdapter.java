package com.example.teamf;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OutfitItemAdapter extends RecyclerView.Adapter<OutfitItemViewHolder> {

    private ArrayList<ClothingItem.Category.Clothes> clothes;
    private Context context;
    private ArrayList<String> checked_positions;
    private ArrayList<ClothingItem.Category.Clothes> display_clothes;
    private boolean btnVis = true;

    public OutfitItemAdapter(ArrayList<ClothingItem.Category.Clothes> clothes, Context contex) {
        this.context = context;
        this.clothes = clothes;
        this.checked_positions = checked_positions;
        this.btnVis = false;
        display_clothes = clothes;
    }

    public OutfitItemAdapter(ArrayList<ClothingItem.Category.Clothes> clothes, Context context, ArrayList<String> checked_positions) {
        this.context = context;
        this.clothes = clothes;
        this.checked_positions = checked_positions;
        display_clothes = new ArrayList<>();
        for (int i = 0; i < checked_positions.size(); i++) {
            display_clothes.add(clothes.get(Integer.parseInt(checked_positions.get(i))));
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.outfit_item_detail;
    }

    @NonNull
    @Override
    public OutfitItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new OutfitItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitItemViewHolder holder, int position) {
        int pos = position;
        ClothingItem.Category.Clothes item = display_clothes.get(position);
        holder.name.setText(item.getName());

        ImageView img = (ImageView) holder.imgView;
        try {
            Picasso.get().load(item.getPic()).into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Category: ");
        sb.append(item.getCategory());
        sb.append("\nSub-Category: ");
        sb.append(item.getSub());
        sb.append("\nColor: ");
        sb.append(item.getColor());
        sb.append("\nStyle: ");
        sb.append(item.getStyle());
        sb.append("\nWeather: ");
        sb.append(item.getWeather());
        holder.details.setText(sb.toString());
        if (!btnVis) {
            holder.btn.setVisibility(View.GONE);
        }

        holder.btn.setOnClickListener(view -> {
            checked_positions.remove(pos);
            display_clothes.remove(item);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return display_clothes.size();
    }

    public ArrayList<String> getCheckedPositions() {
        return checked_positions;
    }
}
