package com.example.teamf.ui;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.teamf.ClothingItem;
import com.example.teamf.EditItemFrag;
import com.example.teamf.MainActivity;
import com.example.teamf.R;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

public class ItemDetailFrag extends Fragment {

    MainActivity myact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.frag_item_detail, container, false);
        myact = (MainActivity) getActivity();
        Toolbar toolbar = (Toolbar) myact.findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Item Details");

        toolbar.findViewById(R.id.backButton).setVisibility(View.VISIBLE);
        NavigationBarView navBar = myact.findViewById(R.id.bottomNavigationView);
        navBar.setVisibility(View.GONE);
        ActionMenuItemView settings = (ActionMenuItemView) myact.findViewById(R.id.settings);
        settings.setVisibility(View.GONE);

        Bundle bundle = this.getArguments();
        String clothingImg = bundle.getString("pic", "");
        String clothingName = bundle.getString("name", "");
        String clothingCategory = bundle.getString("category", "");
        String clothingSubCategory = bundle.getString("subcategory", "");
        String clothingColor = bundle.getString("color", "");
        String clothingStyle = bundle.getString("style", "");
        String clothingWeather = bundle.getString("weather", "");

        ImageButton img = myview.findViewById(R.id.item_pic2);
        img.setEnabled(false);
        //TODO: set the image of the clothing item - pic string in clothingImg variable
        try {
            Picasso.get().load(clothingImg).into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView nameText = myview.findViewById(R.id.name_input2);
        nameText.setText(clothingName);

        TextView categoryText = myview.findViewById(R.id.category_input2);
        categoryText.setText(clothingCategory);

        TextView subCategoryText = myview.findViewById(R.id.sub_input2);
        subCategoryText.setText(clothingSubCategory);

        TextView colorText = myview.findViewById(R.id.color_input2);
        colorText.setText(clothingColor);

        TextView styleText = myview.findViewById(R.id.style_input2);
        styleText.setText(clothingStyle);

        TextView weatherText = myview.findViewById(R.id.weather_input2);
        weatherText.setText(clothingWeather);

        Button editBtn = myview.findViewById(R.id.edit);
        editBtn.setOnClickListener(v -> {
            Fragment editItemFrag = new EditItemFrag();
            editItemFrag.setArguments(bundle);
            FragmentTransaction transaction = myact.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, editItemFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        Button donateBtn = myview.findViewById(R.id.donate_btn);
        donateBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Confirm Donation");
            builder.setMessage("Are you sure you want to donate this item? It will be deleted from your closet.");
            builder.setCancelable(true);
            builder.setPositiveButton("Donate!", (dialog, which) -> {
                ClothingItem.Category.Clothes oldOutfit = new ClothingItem.Category.Clothes(clothingName, clothingImg, clothingSubCategory, clothingColor, clothingStyle, clothingWeather, clothingCategory);
                myact.deleteFromDatabase(myact.getRef(), oldOutfit, true);
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        Button backButton = (Button) toolbar.findViewById(R.id.backButton);
//        Bitmap image = null;
//        try {
//            URL url = new URL(clothingImg);
//            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        //backButton.setImageBitmap(image);
        backButton.setOnClickListener(view -> {
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
                settings.setVisibility(View.VISIBLE);
            }
        });
        settings.setVisibility(View.VISIBLE);
        return myview;
    }
}