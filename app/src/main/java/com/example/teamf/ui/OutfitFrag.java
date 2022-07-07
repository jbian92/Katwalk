package com.example.teamf.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamf.ClothingItem;
import com.example.teamf.MainActivity;
import com.example.teamf.OutfitItem;
import com.example.teamf.OutfitItemListAdapter;
import com.example.teamf.OutfitListener;
import com.example.teamf.R;
import com.example.teamf.Search;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OutfitFrag extends Fragment {
    public String weather;
    private MainActivity myact;
    private Search search;
    private Button button;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private View myView;
    private boolean isFahrenheit;
    private SharedPreferences sharedPref;
    private ArrayList<OutfitItem> outfits = new ArrayList<>();

    public OutfitFrag() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.frag_outfit, container, false);
        myact = (MainActivity) getActivity();

        isFahrenheit = myact.getFahrenheit();
        double lat = myact.getLatitude();
        double lon = myact.getLongitude();

        getWeather(lat > 0.0 ? lat : 39.328888, lon > 0.0 ? lon : -76.620277);

        search = new Search();
        Toolbar toolbar =  myact.findViewById(R.id.toolbar);
        TextView mTitle =  toolbar.findViewById(R.id.toolbar_title);
        toolbar.findViewById(R.id.backButton).setVisibility(View.INVISIBLE);
        mTitle.setText(R.string.title_outfit);
        button = myView.findViewById(R.id.button);
        spinner = myView.findViewById(R.id.spinner);
        NavigationBarView navBar = getActivity().findViewById(R.id.bottomNavigationView);
        navBar.setVisibility(View.VISIBLE);
        //outfits.clear();
        setRecyclerView(myView);

        button.setOnClickListener(view -> {
            //Three outfits
            outfits.clear();
            for (int i = 0; i < 3; i++) {
                //Issue with the API on occasion, this is to make sure it doesn't crash
                if (weather == null) {
                    weather = getResources().getString(R.string.eighty_ninety);
                }
                ArrayList<ClothingItem.Category.Clothes> allClothes = search.generateOutfit(myact, spinner.getSelectedItem().toString(), weather);
                if (allClothes.size() > 0) {
                    OutfitItem temp = new OutfitItem(allClothes);
                    temp.setWeather(weather);
                    temp.setStyle(spinner.getSelectedItem().toString());
                    outfits.add(temp);
                }
                //TODO: PUT EACH ARRAYLIST INTO A SEPARATE OUTFIT
            }
            setRecyclerView(myView);
        });


        return myView;
    }

    protected void getWeather(double latitude, double longitude) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://weatherbit-v1-mashape.p.rapidapi.com/current?lon=" + longitude + "&lat=" + latitude)
                .get()
                .addHeader("X-RapidAPI-Host", "weatherbit-v1-mashape.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "ecc04452b4msh5a88aade725d661p18e18djsnbe694ef35487")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    String responseData = responseBody.string();
                    Double currTemp = Double.parseDouble(responseData.substring(responseData.indexOf("temp") + 6,
                            responseData.indexOf(",\"station"))); // changed from 10 to 9
                    String currWeather = responseData.substring(responseData.indexOf("description") + 13,
                            responseData.indexOf("},\""));
                    //String weatherPrep = getCurrentWeather("clear sky");
                    String weatherPrep = getCurrentWeather(currWeather.toLowerCase());
                    String tempPrep = String.valueOf(Math.round(toFahrenheit(currTemp)));
                   //String tempPrep = String.valueOf(57);
                    response.close();

                    setWeather(weatherPrep, tempPrep);
                }
            }
        });
        //setRecyclerView(myView);
    }

    private String getCurrentWeather(String currWeather) {

        if(currWeather.equals("clear sky")) {
            return "Clear";
        } else if (currWeather.contains("cloud")) {
            return "Cloudy";
        } else if(currWeather.contains("thunderstorm")) {
            return "Stormy";
        } else if(currWeather.contains("rain") || currWeather.contains("drizzle")) {
            return "Rainy";
        } else if (currWeather.contains("snow") || currWeather.contains("hail") ||
                currWeather.contains("sleet")) {
            return "Snowy";
        } else if (currWeather.contains("mist") || currWeather.contains("fog") || currWeather.contains("dust") ||
                currWeather.contains("smoke") || currWeather.contains("haze") || currWeather.contains("sand")) {
            return "Misty";
        } else {
            //Default to sunny
            return "Clear";
        }
    }

    public double toFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32.0;
    }

    public double toCelsius(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    public void setWeather(String weatherSwitch, String temperature) {
        TextView weatherText = (TextView) myView.findViewById(R.id.weather);
        ImageView weatherIcon = (ImageView) myView.findViewById(R.id.weatherIcon);
        TextView temp = (TextView) myView.findViewById(R.id.temp);

        myact.runOnUiThread(() -> {
            switch (weatherSwitch) {
                case "Clear":
                    weatherText.setText(R.string.clear);
                    weatherIcon.setImageResource(R.drawable.sun);
                    break;
                case "Clouds":
                    weatherText.setText(R.string.cloudy);
                    weatherIcon.setImageResource(R.drawable.cloudy);
                    break;
                case "Rain":
                    weatherText.setText(R.string.rainy);
                    weatherIcon.setImageResource(R.drawable.rainy);
                    break;
                case "Snow":
                    weatherText.setText(R.string.snowy);
                    weatherIcon.setImageResource(R.drawable.snowy);
                    break;
                case "Thunderstorm":
                    weatherText.setText(R.string.stormy);
                    weatherIcon.setImageResource(R.drawable.stormy);
                    break;
                default:
                    weatherText.setText(R.string.misty);
                    weatherIcon.setImageResource(R.drawable.misty);
            }

            if (Integer.parseInt(temperature) >= 90) {
                weather = myact.getResources().getString(R.string.greater_ninety);
            } else if (Integer.parseInt(temperature) >= 81) {
                weather = myact.getResources().getString(R.string.eighty_ninety);
            } else if (Integer.parseInt(temperature) >= 71) {
                weather = myact.getResources().getString(R.string.seventy_eighty);
            } else if (Integer.parseInt(temperature) >= 61) {
                weather = myact.getResources().getString(R.string.sixity_seventy);
            }  else if (Integer.parseInt(temperature) >= 51) {
                weather = myact.getResources().getString(R.string.fifty_sixty);
            }   else if (Integer.parseInt(temperature) >= 41) {
                weather = myact.getResources().getString(R.string.fourty_fifty);
            }
            else if (Integer.parseInt(temperature) >= 31) {
                weather = myact.getResources().getString(R.string.thirty_fourty);
            } else {
                weather = myact.getResources().getString(R.string.less_thirty);
            }
            if (isFahrenheit) {
                temp.setText(String.format("%.4s °F", temperature));
            } else {
                temp.setText(String.format("%.4s °C", toCelsius(Double.parseDouble(temperature))));

            }
        });
        //setRecyclerView(myView);

    }

    public void setRecyclerView(View myview) {
        recyclerView = myview.findViewById(R.id.gen_outfit);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(myview.getContext(), 1));
        OutfitListener listener = new OutfitListener(myact);
        recyclerView.setAdapter(new OutfitItemListAdapter(outfits, listener));
        //recyclerView.addItemDecoration(new ClothingItemMargin(getContext()));
    }

}