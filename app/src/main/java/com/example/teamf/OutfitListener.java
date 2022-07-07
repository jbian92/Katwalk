package com.example.teamf;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.teamf.ClothingItem;
import com.example.teamf.MainActivity;
import com.example.teamf.OutfitItem;
import com.example.teamf.OutfitItemListAdapter;
import com.example.teamf.R;
import com.example.teamf.ui.OutfitDetailFrag;

import java.util.Random;

public class OutfitListener implements OutfitItemListAdapter.OnItemClickListener {

    private MainActivity myact;
    private boolean clickable = true;

    public OutfitListener(MainActivity act) {
        myact = act;
    }

    @Override
    public void onItemClick(OutfitItem outfitItem) {
        if (clickable) {
            Bundle bundle = new Bundle();
            int pos = 0;
            for (ClothingItem.Category.Clothes clothingItem : outfitItem.getItems()) {
                bundle.putString("name" + pos, clothingItem.getName());
                bundle.putString("category" + pos, clothingItem.getCategory());
                bundle.putString("image" + pos, clothingItem.getPic());
                bundle.putString("subcategory" + pos, clothingItem.getSub());
                bundle.putString("color" + pos, clothingItem.getColor());
                bundle.putString("style" + pos, clothingItem.getStyle());
                bundle.putString("weather" + pos, clothingItem.getWeather());
                pos++;
            }


            // Go to Item Details screen when user clicks on a clothing item
            Fragment outfitDetailFrag = new OutfitDetailFrag(outfitItem);
            outfitDetailFrag.setArguments(bundle);
            FragmentTransaction transaction = myact.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, outfitDetailFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void wear(OutfitItem outfitItem) {
        if (clickable) {
            for (ClothingItem.Category.Clothes clothingItem : outfitItem.getItems()) {
                myact.addFreq(myact.getRef(), clothingItem);
            }
        }
    }

    @Override
    public void favorite(OutfitItem outfitItem, boolean checked) {
        if (clickable) {
            if (checked) {
                outfitItem.setID(myact.generateRandomString((new Random()).nextInt(100) + 5));
                myact.addFav(myact.getRef(), outfitItem);
            } else {
                myact.removeFav(myact.getRef(), outfitItem);
            }
        }
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

}
