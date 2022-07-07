package com.example.teamf;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddItemRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final TextView view;
    private final ImageView imageView;
    private final Button btn;
    public AddItemRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.clothing_item);
        imageView = itemView.findViewById(R.id.item_image_view);
        btn = itemView.findViewById(R.id.add_btn);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getView(){
        return view;
    }

    public Button getBtn() { return btn; }

}