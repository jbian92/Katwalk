package com.example.teamf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamf.ClothingItemMargin;
import com.example.teamf.DonateLink;
import com.example.teamf.ItemListAdapter;
import com.example.teamf.MainActivity;
import com.example.teamf.R;
import com.example.teamf.ClothingItem;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class DonateFrag extends Fragment implements ItemListAdapter.OnItemClickListener {
    private MainActivity myact;
    private ListView myList;
    private CardView myCard;
    private RecyclerView recyclerView;

    private static final String TAG = "dbref: ";


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.frag_donate, container, false);
        myact = (MainActivity) getActivity();
        Toolbar toolbar = (Toolbar) myact.findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.findViewById(R.id.backButton).setVisibility(View.INVISIBLE);
        mTitle.setText(R.string.title_donate);

        NavigationBarView navBar = getActivity().findViewById(R.id.bottomNavigationView);
        navBar.setVisibility(View.VISIBLE);

        TextView textNumDonated = (TextView) myview.findViewById(R.id.num_donated);
        String textSet = "Items Donated: " + myact.donoCount + " ☺";
        Log.d("String", textSet);
        textNumDonated.setText(textSet);


        //TODO: Hardcode in least worn and be able to display the picture

        ArrayList<ClothingItem.Category.Clothes> clothes = new ArrayList<>();
        for(ArrayList<ClothingItem.Category.Clothes> c: myact.userClothings().values()){
            for(ClothingItem.Category.Clothes item: c){
                clothes.add(item);
            }
        }

        Collections.sort(clothes);
        ArrayList<ClothingItem.Category.Clothes> topThree = new ArrayList<>();
        topThree.add(clothes.get(0));
        topThree.add(clothes.get(1));
        topThree.add(clothes.get(2));

        /*clothes.add(new ClothingItem.Category.Clothes("White Shirt", "pic", "T-Shirt", "White", "Casual", "71-80°F", "Tops"));
        clothes.add(new ClothingItem.Category.Clothes("Blue Jeans", "pic", "Jeans", "Blue", "Casual", "61-70°F", "Bottoms"));
        clothes.add(new ClothingItem.Category.Clothes("White Shoes", "pic", "Trainers", "White", "Casual", "61-70°F", "Shoes"));
        */


        // Create RecyclerView
        recyclerView = myview.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(myview.getContext(), 3));
        recyclerView.setAdapter(new ItemListAdapter(topThree, this));
        recyclerView.addItemDecoration(new ClothingItemMargin(getContext()));

        myList = (ListView) myview.findViewById(R.id.mylist);
        myCard = (CardView) myview.findViewById(R.id.card_view);

        myList.setAdapter(myact.linkAdapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DonateLink selected = myact.links.get(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, selected.getURL());
                startActivity(browserIntent);
            }
        });

        return myview;
    }

    @Override
    public void onItemClick(ClothingItem.Category.Clothes clothing) {
        Bundle bundle = new Bundle();
        bundle.putString("name", clothing.getName());
        // TODO: get category
//        bundle.putString("category", clothing.getCategory());
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