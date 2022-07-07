package com.example.teamf;

import android.net.Uri;

import java.util.ArrayList;

public class ClothingItem {
    private ArrayList<Category> categories;

    public ClothingItem() {
        categories = new ArrayList<>();
    }

    ArrayList<Category> getCategories() {
     return categories;
    }

    public void add(Category catName) {
        categories.add(catName);
    }
    public void remove(Category catName) {
        categories.remove(catName);
    }

    public static class Category {
        private ArrayList<Clothes> clothes;
        private String name;

        public Category(String name) {
            clothes = new ArrayList<>();
            this.name = name;
        }

        public void addClothes(Clothes clothes) {
            this.clothes.add(clothes);
        }

        public void removeClothes(Clothes clothes) {
            this.clothes.remove(clothes);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class Clothes implements Comparable<Clothes> {
            String pic, name, sub, color, style, weather, category;
            int frequency;

            public Clothes() {

            }

            public Clothes(String name, String pic, String sub, String color, String style, String weather, String category) {
                this.pic = pic;
                this.sub = sub;
                this.color = color;
                this.style = style;
                this.weather = weather;
                this.name = name;
                this.category = category;
                frequency = 0;
            }


            public void setCategory(String category) {
                this.category = category;
            }

            public String getCategory() {
                return category;
            }

            public void addFrequency() {
                this.frequency++;
            }

            public int getFrequency() {
                return frequency;
            }


            public void setColor(String color) {
                this.color = color;
            }

            public String getColor() {
                return color;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPic() {
                return pic;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getStyle() {
                return style;
            }

            public void setSub(String sub) {
                this.sub = sub;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getSub() {
                return sub;
            }

            public String getWeather() {
                return weather;
            }

            public void setFreq(int i) { this.frequency = i; }
          
            @Override
            public int compareTo(Clothes clothes) {
                if (this.frequency > clothes.frequency) {
                    return 1;
                } else if (this.frequency < clothes.frequency) {
                    return -1;
                } else {
                    return 0;
                }
            }

        }
    }
}
