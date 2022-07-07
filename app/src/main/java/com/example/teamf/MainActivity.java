package com.example.teamf;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.teamf.ui.ClosetFrag;
import com.example.teamf.ui.DonateFrag;
import com.example.teamf.ui.FavFrag;
import com.example.teamf.ui.OutfitFrag;
import com.google.android.material.navigation.NavigationBarView;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationBarView.OnItemSelectedListener, LocationListener {


    NavigationBarView bottomNavigationView;
    ClosetFrag closet = new ClosetFrag();
    DonateFrag donate = new DonateFrag();
    FavFrag fav = new FavFrag();
    OutfitFrag outfit = new OutfitFrag();
    private FragmentTransaction transaction;
    private ClothingItem userInfo;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;

    private SharedPreferences sharedPref;
    private FirebaseStorage picsbase;
    private StorageReference picsref;

    private String name;
    private String UserID;


    private HashMap<String, ArrayList<ClothingItem.Category.Clothes>> userClothing = new HashMap<>();

    public int donoCount;
    public ArrayList<DonateLink> links;
    public LinkAdapter linkAdapter;

    protected LocationManager locationManager;
    protected Context context;
    String lat;

    protected double latitude;
    protected double longitude;
    private boolean isFahrenheit;
    private ArrayList<OutfitItem> favorites;


    public DatabaseReference getRef() {
        return dbref;
    }

    public StorageReference getStorageRef() {
        return picsref;
    }

    public HashMap<String, ArrayList<ClothingItem.Category.Clothes>> userClothings() {
        return userClothing;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        mdbase = FirebaseDatabase.getInstance();

        UserID = sharedPref.getString("userID", "0");
        if (UserID.equals("null")) {
            UserID = "0";
        }
     /*   if (UserID.equals("0")) {
            UserID = generateRandomString((new Random()).nextInt(100) + 5);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("userID", UserID);
            editor.commit();
        } */

        dbref = mdbase.getReference(String.valueOf(UserID));

        picsbase = FirebaseStorage.getInstance();
        picsref = picsbase.getReference();

        userInfo = new ClothingItem();
        favorites = new ArrayList<>();
        isFahrenheit = sharedPref.getBoolean("isFahrenheit", false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        }


        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getAllClothes(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());

            }

        });

        dbref.child("num_donated").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    dbref.child("num_donated");
                    if (dataSnapshot.getValue() == null) {
                        Log.e("ERROR RETRIEVE", "Num_donations is null.");
                    } else {
                        Log.d("Retrieval", "Num Donated Retrieved");
                        donoCount = dataSnapshot.getValue(Integer.class);
                    }
                } else {
                    donoCount = 0;
                    dbref.child("num_donated").setValue(donoCount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_outfits);
        bottomNavigationView.setItemBackgroundResource(R.drawable.menubackground);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.backButton).setVisibility(View.INVISIBLE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setScrimColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        links = new ArrayList<>();
        addLinksStart();
        linkAdapter = new LinkAdapter(this, R.layout.donate_link_layout, links);


        //Set up settings listeners
    }


    private void settings() {
        ToggleButton fah = findViewById(R.id.off);
        ToggleButton cel = findViewById(R.id.on);

        fah.setChecked(isFahrenheit);
        cel.setChecked(!isFahrenheit);
        SharedPreferences.Editor editor = sharedPref.edit();
        fah.setOnClickListener(view -> {
            if (fah.isChecked()) {
            cel.setChecked(!cel.isChecked());
            editor.putBoolean("isFahrenheit", fah.isChecked());
            editor.commit();
            isFahrenheit = fah.isChecked();} else {
                fah.setChecked(!fah.isChecked());
            }
        });
        cel.setOnClickListener(view -> {
            if (cel.isChecked()) {
                fah.setChecked(!fah.isChecked());
                editor.putBoolean("isFahrenheit", fah.isChecked());
                editor.commit();
                isFahrenheit = fah.isChecked();
            } else {
                cel.setChecked(!cel.isChecked());
        }});
    }


    public void getAllClothes(DataSnapshot dataSnapshot) {
        userClothing.clear();
        favorites.clear();
        for (DataSnapshot ds : dataSnapshot.child("clothes").getChildren()) {
            ArrayList<ClothingItem.Category.Clothes> clothes = new ArrayList<>();
            for (DataSnapshot ds2 : ds.getChildren()) {
                ClothingItem.Category.Clothes c = ds2.getValue(ClothingItem.Category.Clothes.class);
                clothes.add(c);
                updateFavorites(c);
            }

            userClothing.put(ds.getKey(), clothes);
        }
        for (DataSnapshot ds : dataSnapshot.child("favorites").getChildren()) {
            OutfitItem item = new OutfitItem();
            for (DataSnapshot ds2 : ds.child("clothes").getChildren()) {
                ClothingItem.Category.Clothes c = ds2.getValue(ClothingItem.Category.Clothes.class);
                item.addItem(c);
                item.setID(ds.getKey());
            }
            item.setStyle(ds.child("style").getValue(String.class));
            item.setWeather(ds.child("weather").getValue(String.class));
            favorites.add(item);
        }
    }


    private void updateFavorites(ClothingItem.Category.Clothes newClothes) {
        for (OutfitItem item : favorites) {
            for (ClothingItem.Category.Clothes c : item.getItems()) {
                if (c.getName().equals(newClothes.getName())) {
                    dbref.child("favorites").child(item.getID()).child("clothes").child(c.getName()).setValue(newClothes);
                }
            }
        }
    }

    private void addLinksStart() {
        DonateLink goodwill = new DonateLink("Goodwill", "https://www.goodwill.org/donate/donate-stuff/");
        DonateLink sbhs = new DonateLink("The Baltimore Station", "https://baltimorestation.org/donate/");
        DonateLink franCenter = new DonateLink("Franciscan Center", "https://fcbmore.org/get-involved/donations-we-accept/");

        links.add(goodwill);
        links.add(sbhs);
        links.add(franCenter);
    }


    /** Uploads new clothing to database.
     *
     */
    public void uploadToDatabase(StorageReference picsref, Uri selectedImage, DatabaseReference dbref,
                                 String cat, ClothingItem.Category.Clothes clothes) {
        StorageReference uploadRef = picsref.child("path/" + Calendar.getInstance().getTime().toString());
        uploadRef.putFile(selectedImage).addOnCompleteListener(task -> {
            final String[] downloadUrl = new String[1];
            uploadRef.getDownloadUrl().addOnSuccessListener(uri -> {
                downloadUrl[0] = uri.toString();
                clothes.setPic(downloadUrl[0]);
                dbref.child("clothes").child(cat).child(clothes.getName()).setValue(clothes);
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().popBackStack();
                Toast.makeText(this, "Added Clothing!", Toast.LENGTH_SHORT).show();
            });

        });

    }

    public void updateToDatabase(DatabaseReference dbref, String cat, ClothingItem.Category.Clothes clothes,
                                 ClothingItem.Category.Clothes oldClothes, Uri selectedImage) {
        updateFavorites(clothes);
        dbref.child("clothes").child(oldClothes.getCategory()).child(oldClothes.getName()).removeValue();
        if (selectedImage != null) {
            try {
                StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(oldClothes.getPic());
                photoRef.delete().addOnSuccessListener(aVoid -> {
                    Log.d("Storage", "Photo deleted");
                }).addOnFailureListener(
                        exception -> Log.d("Storage", "Photo delete failed"));
            } catch (Exception e) {
                Log.d("Storage", "Photo deletion failed");
            }

            StorageReference uploadRef = picsref.child("path/" + Calendar.getInstance().getTime().toString());
            uploadRef.putFile(selectedImage).addOnCompleteListener(task -> {
                final String[] downloadUrl = new String[1];
                uploadRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    downloadUrl[0] = uri.toString();
                    clothes.setPic(downloadUrl[0]);
                    dbref.child("clothes").child(cat).child(clothes.getName()).setValue(clothes);
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().popBackStack();
                    Toast.makeText(this, "Updated Clothing!", Toast.LENGTH_SHORT).show();
                });

            });
        } else {
            dbref.child("clothes").child(cat).child(clothes.getName()).setValue(clothes).addOnCompleteListener(task -> {
                clothes.setPic(oldClothes.getPic());
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().popBackStack();
                Toast.makeText(this, "Updated Clothing!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    public void addFreq(DatabaseReference dbref, ClothingItem.Category.Clothes clothes) {
        dbref.child("clothes").child(clothes.getCategory()).child(clothes.getName()).child("frequency").setValue(clothes.getFrequency() + 1);
        clothes.setFreq(clothes.getFrequency() + 1);
        updateFavorites(clothes);
    }

    public void addFav(DatabaseReference dbref, OutfitItem outfitItem) {
        for (ClothingItem.Category.Clothes clothingItem : outfitItem.getItems()) {
            dbref.child("favorites").child(outfitItem.getID()).child("clothes").child(clothingItem.getName()).setValue(clothingItem);
        }
        dbref.child("favorites").child(outfitItem.getID()).child("weather").setValue(outfitItem.getWeather());
        dbref.child("favorites").child(outfitItem.getID()).child("style").setValue(outfitItem.getStyle());

    }

    public void removeFav(DatabaseReference dbref, OutfitItem outfitItem) {
        dbref.child("favorites").child(outfitItem.getID()).removeValue();
    }

    public ArrayList<OutfitItem> getFavorites() {
        return favorites;
    }

    public String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            CharSequence alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return sb.toString();
    }

    public void deleteFromDatabase(DatabaseReference dbref, ClothingItem.Category.Clothes oldClothes, Boolean donate) {
        dbref.child("clothes").child(oldClothes.getCategory()).child(oldClothes.getName()).removeValue();
        try {
            StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(oldClothes.getPic());
            photoRef.delete().addOnSuccessListener(aVoid -> {
                Log.d("Storage", "Photo deleted");
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().popBackStack();
                if (donate) {
                    Toast.makeText(this, "Donated "+ oldClothes.getName() +"!", Toast.LENGTH_SHORT).show();
                    incrementDonation(dbref);
                } else {
                    Toast.makeText(this, "Removed Clothing!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(
                    exception -> Log.d("Storage", "Photo delete failed"));
        } catch(Exception e) {
            Log.d("Storage", "Photo deletion failed");
        }
    }

    private void incrementDonation(DatabaseReference dbref) {
        dbref.child("num_donated").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    dbref.child("num_donated");
                    if (dataSnapshot.getValue() == null) {
                        Log.e("ERROR RETRIEVE", "Num_donations is null.");
                    } else {
                        Log.d("Retrieval", "Num Donated Retrieved");
                        donoCount++;
                        dbref.child("num_donated").setValue(donoCount);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });

    }


    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        Log.d("Latitude","location");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lat = String.valueOf(latitude);
        Log.d("Latitude",lat);
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (id == R.id.nav_outfits) {
            transaction = getSupportFragmentManager().beginTransaction();
            drawer.closeDrawer(GravityCompat.END);
            transaction.replace(R.id.container, outfit).commit();
        } else if (id == R.id.nav_closet) {
            drawer.closeDrawer(GravityCompat.END);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, closet).commit();
        } else if (id == R.id.nav_fav) {
            transaction = getSupportFragmentManager().beginTransaction();
            drawer.closeDrawer(GravityCompat.END);
            transaction.replace(R.id.container, fav).commit();
        } else if (id == R.id.nav_donate) {
            transaction = getSupportFragmentManager().beginTransaction();
            drawer.closeDrawer(GravityCompat.END);
            transaction.replace(R.id.container, donate).commit();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
                settings();
            }
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public boolean getFahrenheit() {
        return isFahrenheit;
    }

}