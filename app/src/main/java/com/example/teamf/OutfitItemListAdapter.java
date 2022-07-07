package com.example.teamf;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OutfitItemListAdapter extends RecyclerView.Adapter<OutfitRecyclerViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(OutfitItem outfitItem);
        void wear(OutfitItem outfitItem);
        void favorite(OutfitItem outfitItem, boolean checked);
    }

    private final ArrayList<OutfitItem> outfits;
    private final OnItemClickListener listener;
    private int position;

    private FragmentTransaction transaction;
    private ClothingItem userInfo;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;

    private SharedPreferences sharedPref;
    private FirebaseStorage picsbase;
    private StorageReference picsref;

    private String name;
    private int UserID;

    public OutfitItemListAdapter(ArrayList<OutfitItem> outfits, OnItemClickListener listener) {
        this.outfits = outfits;
        this.listener = listener;

        //sharedPref = getPreferences(Context.MODE_PRIVATE);
        mdbase = FirebaseDatabase.getInstance();
        UserID = 0;
        dbref = mdbase.getReference(String.valueOf(UserID));

        picsbase = FirebaseStorage.getInstance();
        picsref = picsbase.getReference();

        userInfo = new ClothingItem();

        //isFahrenheit = sharedPref.getBoolean("isFahrenheit", false);
    }

    @Override
    public void onBindViewHolder(OutfitRecyclerViewHolder holder, int position) {
        // Displays clothes in recyclerview
        holder.bind(outfits.get(position), listener);
        position = position;
        int i = 0;
        for (ClothingItem.Category.Clothes clothes : outfits.get(position).getItems()) {

            try {
                Picasso.get().load(clothes.getPic()).into(holder.getImages().get(i));
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                i++;
            }
        }
    }

    @Override
    public int getItemCount() {
        return outfits.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.clothing_item;
    }

    @NonNull
    @Override
    public OutfitRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outfit_item, parent, false);
        return new OutfitRecyclerViewHolder(view);
    }

}
