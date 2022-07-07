package com.example.teamf;

import java.util.ArrayList;

public class OutfitItem {
    private ArrayList<ClothingItem.Category.Clothes> items;
    private String ID;
    private String weather;
    private String style;

    public OutfitItem(ArrayList<ClothingItem.Category.Clothes> list) {
        items = list;
    }

    public OutfitItem() {
        items = new ArrayList<>();
    }

    public void setID(String id) {
        this.ID = id;
    }

    public void setWeather(String weather) { this.weather = weather; }

    public void setStyle(String style) { this.style = style; }

    public String getWeather() { return weather; }

    public String getStyle() { return style; }

    public String getID() {
        return ID;
    }

    public void addItem(ClothingItem.Category.Clothes item) {
        items.add(item);
    }

    public ArrayList<ClothingItem.Category.Clothes> getItems() {
        return items;
    }
}
