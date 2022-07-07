package com.example.teamf.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamf.R;




public class OutfitViewHolder extends RecyclerView.ViewHolder {

    private final TextView view;
    private final ImageView imageView;
    private final TextView desc;
    private final TextView name;

    public OutfitViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.outfitDetailBox);
        imageView = itemView.findViewById(R.id.outfitDetailImage);
        desc = itemView.findViewById(R.id.outfitDetailDesc);
        name = itemView.findViewById(R.id.outfitDetailName);
    }


    public ImageView getImageView() {
        return imageView;
    }

    public TextView getView(){
        return view;
    }

    public TextView getDesc() { return desc; }

    public TextView getName() { return name; }


}