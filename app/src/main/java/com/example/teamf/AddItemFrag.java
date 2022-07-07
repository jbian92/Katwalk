package com.example.teamf;

import static android.widget.Toast.LENGTH_SHORT;

import android.Manifest;

import android.content.Intent;
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

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

public class AddItemFrag extends Fragment {

    private AppCompatSpinner spinCategory;
    private AppCompatSpinner spinSubCat;
    private AppCompatSpinner spinColor;
    private AppCompatSpinner spinStyle;
    private AppCompatSpinner spinWeather;
    private String categoryVal;
    private ClothingItem userInfo;
    private Uri imageUri;
    private String imagePath = "";

    MainActivity myact;
    ImageButton imageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.frag_add_item, container, false);
        myact = (MainActivity) getActivity();
        Toolbar toolbar = (Toolbar) myact.findViewById(R.id.toolbar);
        ActionMenuItemView settings = (ActionMenuItemView) myact.findViewById(R.id.settings);
        settings.setVisibility(View.GONE);
        toolbar.findViewById(R.id.backButton).setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Add Item");

        NavigationBarView navBar = myact.findViewById(R.id.bottomNavigationView);
        imageButton = (ImageButton) myview.findViewById(R.id.item_pic);
        navBar.setVisibility(View.GONE);

        spinCategory = (AppCompatSpinner) myview.findViewById(R.id.category_input);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(myact, R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(categoryAdapter);

        spinColor = (AppCompatSpinner) myview.findViewById(R.id.color_input);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(myact, R.array.color, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinColor.setAdapter(colorAdapter);

        spinStyle = (AppCompatSpinner) myview.findViewById(R.id.style_input);
        ArrayAdapter<CharSequence> styleAdapter = ArrayAdapter.createFromResource(myact, R.array.style, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStyle.setAdapter(styleAdapter);

        spinWeather = (AppCompatSpinner) myview.findViewById(R.id.weather_input);
        ArrayAdapter<CharSequence> weatherAdapter = ArrayAdapter.createFromResource(myact, R.array.weather, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWeather.setAdapter(weatherAdapter);

        spinSubCat = (AppCompatSpinner) myview.findViewById(R.id.sub_input);
        ArrayAdapter<CharSequence> subAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_tops, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSubCat.setAdapter(subAdapter);

        // Change sub-category dropdown values depending on category chosen
        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                ArrayAdapter<CharSequence> subAdapter;
                if (spinCategory.getSelectedItem().equals("Tops")) {
                    subAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_tops, android.R.layout.simple_spinner_item);
                } else if (spinCategory.getSelectedItem().equals("Bottoms")) {
                    subAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_bottoms, android.R.layout.simple_spinner_item);
                } else if (spinCategory.getSelectedItem().equals("Dresses")) {
                    subAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_dresses, android.R.layout.simple_spinner_item);
                } else if (spinCategory.getSelectedItem().equals("Shoes")) {
                    subAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_shoes, android.R.layout.simple_spinner_item);
                } else {
                    subAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_accessories, android.R.layout.simple_spinner_item);
                }
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinSubCat.setAdapter(subAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        Button saveBtn = myview.findViewById(R.id.save_button);
        saveBtn.setOnClickListener(v -> {
            EditText text = myview.findViewById(R.id.name_input);
            String name = text.getText().toString(); //add toast

            if (name.equals("")) {
                Toast.makeText(getActivity().getApplicationContext(), "Please enter an item.", LENGTH_SHORT).show();
            } else if (imagePath.equals("")){
                Toast.makeText(getActivity().getApplicationContext(), "Please upload an image of your item.", LENGTH_SHORT).show();
            } else {
                String category = spinCategory.getSelectedItem().toString();
                String subCategory = spinSubCat.getSelectedItem().toString();
                String color = spinColor.getSelectedItem().toString();
                String style = spinStyle.getSelectedItem().toString();
                String weather = spinWeather.getSelectedItem().toString();

                ClothingItem.Category.Clothes newOutfit = new ClothingItem.Category.Clothes(name, "", subCategory, color, style, weather, category);
                int catPos = (category.equals("Tops")) ? 0 : (category.equals("Bottoms")) ? 1 : (category.equals("Dresses")) ? 2 : (category.equals("Shoes")) ? 3 : 4;
                String[] clothes = {"Tops", "Bottoms", "Dresses", "Shoes", "Accessories"};

                myact.uploadToDatabase(myact.getStorageRef(), imageUri, myact.getRef(), clothes[catPos], newOutfit);
            }
        });

        Fragment frag = this;
        imageButton.setOnClickListener(view -> {
            askPermissions();
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);

            startActivityForResult(intent, 100);
        });

        Button backButton = (Button) toolbar.findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            FragmentManager fm = getParentFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
                settings.setVisibility(View.VISIBLE);
            }
        });


        return myview;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == -1 && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = this.getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imageUri  = selectedImage;
            imagePath = picturePath;
            //System.out.println(url);

            imageButton.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }


    private void askPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

    }

}
