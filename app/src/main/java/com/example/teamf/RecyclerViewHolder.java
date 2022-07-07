package com.example.teamf;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView view;
    private ImageView imageView;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.clothing_item);
        imageView = itemView.findViewById(R.id.clothingImage1);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getView(){
        return view;
    }

    public void bind(final ClothingItem.Category.Clothes item, final ItemListAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(v -> listener.onItemClick(item));
    }
}