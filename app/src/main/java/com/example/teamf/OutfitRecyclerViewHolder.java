package com.example.teamf;

import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class OutfitRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ArrayList<ImageView> images = new ArrayList<>();
    private OutfitItemListAdapter.OnItemClickListener listener;
    public ImageView image1;
    public ImageView image2;
    public ImageView image3;
    public ImageView image4;
    private Button wear;
    private ToggleButton favorite;


    public OutfitRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        image1 = itemView.findViewById(R.id.outfitImage01);

        image2 = itemView.findViewById(R.id.outfitImage2);
        image3 = itemView.findViewById(R.id.outfitImage3);
        image4 = itemView.findViewById(R.id.outfitImage4);

        images.add(itemView.findViewById(R.id.outfitImage01));
        images.add(itemView.findViewById(R.id.outfitImage2));
        images.add(itemView.findViewById(R.id.outfitImage3));
        images.add(itemView.findViewById(R.id.outfitImage4));
        wear = itemView.findViewById(R.id.wear_btn);
        favorite = itemView.findViewById(R.id.fav_btn);


    }

    @Override
    public void onClick(View v) { }


    public ArrayList<ImageView> getImages() {
        return images;
    }

    public void bind(final OutfitItem item, final OutfitItemListAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(v -> listener.onItemClick(item));
        wear.setOnClickListener(v ->
        {
            listener.wear(item);
            wear.setEnabled(false);
        });
        favorite.setOnClickListener(v -> {
            listener.favorite(item, favorite.isChecked());
        });

    }

    public ToggleButton getFavorite() {
        return favorite;
    }
}
