package com.example.teamf;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddItemListAdapter extends RecyclerView.Adapter<AddItemRecyclerViewHolder> {

    private ArrayList<ClothingItem.Category.Clothes> clothes;
    private Context context;
    private ArrayList<String> checked_positions;
    private boolean clickable = true;

    public AddItemListAdapter(ArrayList<ClothingItem.Category.Clothes> clothes, Context context, ArrayList<String> checked_positions) {
        this.clothes = clothes;
        this.context = context;
        this.checked_positions = checked_positions;
    }

    @Override
    public void onBindViewHolder(AddItemRecyclerViewHolder holder, int position) {
        int pos = position;
        holder.getView().setTextColor(Color.BLACK);
        holder.getView().setText("");
        ImageView img = (ImageView) holder.getImageView();
        try {
            Picasso.get().load(clothes.get(position).getPic()).into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btn = (Button) holder.getBtn();
        if (checked_positions.contains(Integer.toString(pos))) {
            btn.setTag("add");
            btn.setBackground(ContextCompat.getDrawable(context, R.drawable.check));
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickable) {
                    if (btn.getTag() == "add") {
                        btn.setTag("check");
                        btn.setBackground(ContextCompat.getDrawable(context, R.drawable.add_plus_icon));
                        checked_positions.remove(Integer.toString(pos));
                    } else {
                        btn.setTag("add");
                        btn.setBackground(ContextCompat.getDrawable(context, R.drawable.check));
                        checked_positions.add(Integer.toString(pos));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.add_clothing_item;
    }

    @NonNull
    @Override
    public AddItemRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new AddItemRecyclerViewHolder(view);
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
