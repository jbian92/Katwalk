package com.example.teamf.ui;

import android.os.Bundle;

import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamf.AddItemListAdapter;
import com.example.teamf.ClothingItem;
import com.example.teamf.ClothingItemMargin;
import com.example.teamf.EditItemFrag;
import com.example.teamf.MainActivity;
import com.example.teamf.OutfitDetailAdapter;
import com.example.teamf.OutfitItem;
import com.example.teamf.OutfitItemAdapter;
import com.example.teamf.OutfitItemListAdapter;
import com.example.teamf.OutfitListener;
import com.example.teamf.R;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OutfitDetailFrag extends Fragment {

    MainActivity myact;
    RecyclerView recyclerView;
    Bundle bundle;
    OutfitItem outfits;

    public OutfitDetailFrag(OutfitItem outfitItem) {
        // Required empty public constructor
        outfits = outfitItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.frag_outfit_details, container, false);
        myact = (MainActivity) getActivity();
        Toolbar toolbar = (Toolbar) myact.findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Outfit Details");

        toolbar.findViewById(R.id.backButton).setVisibility(View.VISIBLE);
        NavigationBarView navBar = myact.findViewById(R.id.bottomNavigationView);
        navBar.setVisibility(View.GONE);
        ActionMenuItemView settings = (ActionMenuItemView) myact.findViewById(R.id.settings);
        settings.setVisibility(View.GONE);

        bundle = this.getArguments();


        Button backButton = (Button) toolbar.findViewById(R.id.backButton);

        backButton.setOnClickListener(view -> {
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
                settings.setVisibility(View.VISIBLE);
            }
        });

        ArrayList<ClothingItem.Category.Clothes> clothes = new ArrayList<>();
        //public Clothes(String name, String pic, String sub, String color, String style, String weather, String category) {


        setRecyclerView(myview, outfits.getItems());
        return myview;
    }

    /* public void setRecyclerView(View myview) {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(myview.getContext(), 1));
        OutfitListener listener = new OutfitListener(myact);
      //  recyclerView.setAdapter(new OutfitDetailAdapter(bundle, listener));
        //recyclerView.addItemDecoration(new ClothingItemMargin(getContext()));
    } */

    public void setRecyclerView(View myview, ArrayList<ClothingItem.Category.Clothes> clothes) {
        recyclerView = myview.findViewById(R.id.outfit_display);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(myview.getContext()));
        recyclerView.setAdapter(new OutfitItemAdapter(clothes, myview.getContext()));
        recyclerView.addItemDecoration(new ClothingItemMargin(getContext()));
    }

    //@Override
    public void onItemClick(ClothingItem.Category.Clothes clothing) {
        Bundle bundle = new Bundle();
        bundle.putString("name", clothing.getName());
        bundle.putString("category", clothing.getCategory());
        bundle.putString("subcategory", clothing.getSub());
        bundle.putString("color", clothing.getColor());
        bundle.putString("style", clothing.getStyle());
        bundle.putString("weather", clothing.getWeather());
        bundle.putString("pic", clothing.getPic());

        // Go to Item Details screen when user clicks on a clothing item
        Fragment itemDetailFrag = new ItemDetailFrag();
        itemDetailFrag.setArguments(bundle);
        FragmentTransaction transaction = myact.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, itemDetailFrag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}