package com.example.teamf;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OutfitItemViewHolder extends RecyclerView.ViewHolder {

    public Button btn;
    public TextView name;
    public TextView details;
    public ImageView imgView;

    public OutfitItemViewHolder(@NonNull View itemView) {
        super(itemView);
        btn = itemView.findViewById(R.id.delete_clothing_item_btn);
        name = itemView.findViewById(R.id.name);
        details = itemView.findViewById(R.id.details);
        imgView = itemView.findViewById(R.id.add_item_img);
    }




}
