package com.example.teamf;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Search {

    public ArrayList<ClothingItem.Category.Clothes> filter(MainActivity myact, String category, String color, String style, String weather) {
        ArrayList<ClothingItem.Category.Clothes> result = new ArrayList<>();
        HashMap<String, ArrayList<ClothingItem.Category.Clothes>> allClothes = myact.userClothings();
        for (String key : allClothes.keySet()) {
            for (ClothingItem.Category.Clothes clothes : Objects.requireNonNull(allClothes.get(key))) {
                if ((category != null && category.equals(clothes.category)) || (color != null && color.equals(clothes.color)) ||
                        (style != null && style.equals(clothes.style))  || (weather != null && weather.equals(clothes.weather))) {
                //if ((category == null || category.equals(clothes.category)) && (color == null || color.equals(clothes.color)) &&
                        //(style != null || style.equals(clothes.style)) && (weather == null || weather.equals(clothes.weather))) {
                    result.add(clothes);
                }
            }
        }
        return result;
    }

    public ArrayList<ClothingItem.Category.Clothes> search(MainActivity myact, String query) {
        ArrayList<ClothingItem.Category.Clothes> result = new ArrayList<>();
        ArrayList<ClothingItem.Category.Clothes> allClothes = getAllClothing(myact);
        for (ClothingItem.Category.Clothes clothes : allClothes) {
            if (query.equals("") || clothes.name.toLowerCase().contains(query.toLowerCase())) {
                result.add(clothes);
            }
        }
        return result;
    }

    public ArrayList<ClothingItem.Category.Clothes> getAllClothing(MainActivity myact) {
        HashMap<String,ArrayList<ClothingItem.Category.Clothes>> clothes = myact.userClothings();
        ArrayList<ClothingItem.Category.Clothes> allClothes = new ArrayList<>();
        for (String key : clothes.keySet()) {
            allClothes.addAll(Objects.requireNonNull(clothes.get(key)));
        }
        return allClothes;
    }

    public ArrayList<ClothingItem.Category.Clothes> generateOutfit(MainActivity myact, String style, String weather) {
        ArrayList<ClothingItem.Category.Clothes> result = new ArrayList<>();
        HashMap<String, ArrayList<ClothingItem.Category.Clothes>> allClothes = myact.userClothings();
        HashMap<String, ArrayList<ClothingItem.Category.Clothes>> availableClothes = new HashMap<>();
        for (String key : allClothes.keySet()) {
            for (ClothingItem.Category.Clothes clothes : Objects.requireNonNull(allClothes.get(key))) {
                if (style.equals(clothes.style) &&
                        (weather.equals(clothes.weather) ||
                                clothes.category.equals("Shoes") || (clothes.category.equals("Accessories")))) {
                    if (!availableClothes.containsKey(key)) {
                        availableClothes.put(key, new ArrayList<>());
                    }
                    availableClothes.get(key).add(clothes);
                }
            }
        }
        //Finally, choose a random outfit
        for (String key : availableClothes.keySet()) {
            result.add(availableClothes.get(key).get((int) (Math.random() * availableClothes.get(key).size())));
        }
        return result;
    }


}
