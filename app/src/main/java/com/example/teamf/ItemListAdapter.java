package com.example.teamf;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ClothingItem.Category.Clothes clothing);
    }

    private final ArrayList<ClothingItem.Category.Clothes> clothes;
    private final OnItemClickListener listener;

    public ItemListAdapter(ArrayList<ClothingItem.Category.Clothes> clothes, OnItemClickListener listener) {
        this.clothes = clothes;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        // Displays clothes in recyclerview
        holder.bind(clothes.get(position), listener);
        holder.getView().setTextColor(Color.BLACK);
        holder.getView().setText("");
                //+ clothes.get(position).getName());
        ImageView img = (ImageView) holder.getImageView();
        try {
            Picasso.get().load(clothes.get(position).getPic()).into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.clothing_item;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }
}
