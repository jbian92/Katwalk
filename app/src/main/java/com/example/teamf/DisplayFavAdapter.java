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

public class DisplayFavAdapter extends RecyclerView.Adapter<OutfitRecyclerViewHolder> {

    OutfitListener listener;

    private ArrayList<OutfitItem> clothes;

    public DisplayFavAdapter(ArrayList<OutfitItem> clothes, OutfitListener listener) {
        this.clothes = clothes;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.outfit_item;
    }

    @NonNull
    @Override
    public OutfitRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new OutfitRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitRecyclerViewHolder holder, int position) {
        holder.bind(clothes.get(position), listener);
        holder.getFavorite().setChecked(true);
        int i = 0;
        for (ClothingItem.Category.Clothes item : clothes.get(position).getItems()) {
            try {
                Picasso.get().load(item.getPic()).into(holder.getImages().get(i));
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                i++;
            }
        }
    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

}
