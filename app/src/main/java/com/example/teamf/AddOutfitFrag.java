package com.example.teamf;

import static android.widget.Toast.LENGTH_SHORT;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teamf.ui.ItemDetailFrag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class AddOutfitFrag extends Fragment {

    private MainActivity myact;
    private ImageButton imageButton;
    private FloatingActionButton fab;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ArrayList<String> checked_positions;
    private RecyclerView recyclerView;
    private Search search;
    private AppCompatSpinner spinStyle;
    private AppCompatSpinner spinWeather;
    private ArrayList<ClothingItem.Category.Clothes> all_clothes;
    OutfitItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.frag_add_outfit, container, false);
        myact = (MainActivity) getActivity();
        Toolbar toolbar = (Toolbar) myact.findViewById(R.id.toolbar);
        ActionMenuItemView settings = (ActionMenuItemView) myact.findViewById(R.id.settings);
        settings.setVisibility(View.GONE);
        toolbar.findViewById(R.id.backButton).setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Create Outfit");

        search = new Search();

        NavigationBarView navBar = myact.findViewById(R.id.bottomNavigationView);
        imageButton = (ImageButton) myview.findViewById(R.id.item_pic);
        navBar.setVisibility(View.GONE);

        preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = preferences.edit();
        checked_positions = getArray("added items");

        all_clothes = search.getAllClothing(myact);
        setRecyclerView(myview, all_clothes);

        fab = myview.findViewById(R.id.add_to_outfit_fab);
        fab.setOnClickListener(view -> {
            saveArray(adapter.getCheckedPositions(), "added items");
            Fragment addItemToOutfitFrag = new AddItemToOutfitFrag();
            FragmentTransaction transaction = myact.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, addItemToOutfitFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        spinStyle = (AppCompatSpinner) myview.findViewById(R.id.style_input);
        ArrayAdapter<CharSequence> styleAdapter = ArrayAdapter.createFromResource(myact, R.array.style, android.R.layout.simple_spinner_item);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStyle.setAdapter(styleAdapter);

        spinWeather = (AppCompatSpinner) myview.findViewById(R.id.weather_input);
        ArrayAdapter<CharSequence> weatherAdapter = ArrayAdapter.createFromResource(myact, R.array.weather, android.R.layout.simple_spinner_item);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWeather.setAdapter(weatherAdapter);

        Button saveBtn = myview.findViewById(R.id.save_button);
        saveBtn.setOnClickListener(v -> {
            String style = spinStyle.getSelectedItem().toString();
            String weather = spinWeather.getSelectedItem().toString();

            ArrayList<ClothingItem.Category.Clothes> added_clothes = new ArrayList<>();
            for (int i = 0; i < checked_positions.size(); i++) {
                added_clothes.add(all_clothes.get(Integer.parseInt(checked_positions.get(i))));
            }

            OutfitItem outfit = new OutfitItem(added_clothes);
            outfit.setID(myact.generateRandomString((new Random()).nextInt(100) + 5));
            outfit.setWeather(weather);
            outfit.setStyle(style);
            myact.addFav(myact.getRef(), outfit);

            // Return to Favorite Outfits screen
            Toast.makeText(getContext(), "Added Outfit", LENGTH_SHORT).show();
            editor.remove("added items").apply();
            FragmentManager fm = getParentFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            }
        });


        Button backButton = (Button) toolbar.findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            editor.remove("added items").apply();
            FragmentManager fm = getParentFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            }
        });

        return myview;
    }

    public void saveArray(ArrayList<String> list, String key) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<String> getArray(String key) {
        Gson gson = new Gson();
        String json = preferences.getString(key, null);
        if (json == null) {
            return new ArrayList<String>();
        }
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public void setRecyclerView(View myview, ArrayList<ClothingItem.Category.Clothes> clothes) {
        recyclerView = myview.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(myview.getContext(), 1));
        adapter = new OutfitItemAdapter(clothes, myview.getContext(), checked_positions);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        editor.remove("added items").apply();
        super.onDestroyView();
    }

}