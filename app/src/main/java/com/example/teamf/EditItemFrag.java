package com.example.teamf;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

public class EditItemFrag extends Fragment {

    private MainActivity myact;
    private EditText nameText;
    private ImageButton imageButton;
    private AppCompatSpinner spinCategory;
    private AppCompatSpinner spinSubCat;
    private AppCompatSpinner spinColor;
    private AppCompatSpinner spinStyle;
    private AppCompatSpinner spinWeather;
    private String categoryVal;
    private ClothingItem userInfo;
    private boolean firstTime;
    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firstTime = true;

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.frag_edit_item, container, false);
        myact = (MainActivity) getActivity();
        Toolbar toolbar = (Toolbar) myact.findViewById(R.id.toolbar);
        ActionMenuItemView settings = (ActionMenuItemView) myact.findViewById(R.id.settings);
        settings.setVisibility(View.GONE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Edit Item");
        toolbar.findViewById(R.id.backButton).setVisibility(View.VISIBLE);
        imageButton = (ImageButton) myview.findViewById(R.id.item_pic3);



        NavigationBarView navBar = myact.findViewById(R.id.bottomNavigationView);
        navBar.setVisibility(View.GONE);

        Bundle bundle = this.getArguments();
        String clothingImg = bundle.getString("pic", "");
        String clothingName = bundle.getString("name", "");
        String clothingCategory = bundle.getString("category", "");
        String clothingSubCategory = bundle.getString("subcategory", "");
        String clothingColor = bundle.getString("color", "");
        String clothingStyle = bundle.getString("style", "");
        String clothingWeather = bundle.getString("weather", "");

        try {
            Picasso.get().load(clothingImg).into(imageButton);
        } catch (Exception e) {
            Log.d("error", e.toString());

        }
        nameText = myview.findViewById(R.id.name_input3);
        nameText.setText(clothingName);

        //TODO: get category
        int selectionPosition;
        spinCategory = (AppCompatSpinner) myview.findViewById(R.id.category_input3);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(myact, R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(categoryAdapter);
        selectionPosition = categoryAdapter.getPosition(clothingCategory);
        spinCategory.setSelection(selectionPosition);

        // Change sub-category dropdown values depending on category chosen
        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (!firstTime) {
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
                firstTime = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinColor = (AppCompatSpinner) myview.findViewById(R.id.color_input3);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(myact, R.array.color, android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinColor.setAdapter(colorAdapter);
        selectionPosition= colorAdapter.getPosition(clothingColor);
        spinColor.setSelection(selectionPosition);

        spinSubCat = (AppCompatSpinner) myview.findViewById(R.id.sub_input3);
        ArrayAdapter<CharSequence> subCatAdapter;
        if (spinCategory.getSelectedItem().equals("Tops")) {
            subCatAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_tops, android.R.layout.simple_spinner_item);
        } else if (spinCategory.getSelectedItem().equals("Bottoms")) {
            subCatAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_bottoms, android.R.layout.simple_spinner_item);
        } else if (spinCategory.getSelectedItem().equals("Dresses")) {
            subCatAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_dresses, android.R.layout.simple_spinner_item);
        } else if (spinCategory.getSelectedItem().equals("Shoes")) {
            subCatAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_shoes, android.R.layout.simple_spinner_item);
        } else {
            subCatAdapter = ArrayAdapter.createFromResource(myact, R.array.sub_accessories, android.R.layout.simple_spinner_item);
        }
        subCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSubCat.setAdapter(subCatAdapter);
        selectionPosition = subCatAdapter.getPosition(clothingSubCategory);
        spinSubCat.setSelection(selectionPosition);

        spinStyle = (AppCompatSpinner) myview.findViewById(R.id.style_input3);
        ArrayAdapter<CharSequence> styleAdapter = ArrayAdapter.createFromResource(myact, R.array.style, android.R.layout.simple_spinner_item);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStyle.setAdapter(styleAdapter);
        selectionPosition= styleAdapter.getPosition(clothingStyle);
        spinStyle.setSelection(selectionPosition);

        spinWeather = (AppCompatSpinner) myview.findViewById(R.id.weather_input3);
        ArrayAdapter<CharSequence> weatherAdapter = ArrayAdapter.createFromResource(myact, R.array.weather, android.R.layout.simple_spinner_item);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWeather.setAdapter(weatherAdapter);
        selectionPosition= weatherAdapter.getPosition(clothingWeather);
        spinWeather.setSelection(selectionPosition);

        Fragment frag = this;
        imageButton.setOnClickListener(view -> {
            askPermissions();
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);
        });

        Button backButton = (Button) toolbar.findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
                settings.setVisibility(View.VISIBLE);
            }
        });

        Button saveBtn = myview.findViewById(R.id.save_button2);
        saveBtn.setOnClickListener(v -> {
            String name = nameText.getText().toString();
            String category = spinCategory.getSelectedItem().toString();
            String subCategory = spinSubCat.getSelectedItem().toString();
            String color = spinColor.getSelectedItem().toString();
            String style = spinStyle.getSelectedItem().toString();
            String weather = spinWeather.getSelectedItem().toString();
            

            ClothingItem.Category.Clothes newOutfit = new ClothingItem.Category.Clothes(name, clothingImg, subCategory, color, style, weather, category);
            String[] clothes = {"Tops", "Bottoms", "Dresses", "Shoes", "Accessories"};
            int catPos = (category.equals("Tops")) ? 0 : (category.equals("Bottoms")) ? 1 : (category.equals("Dresses")) ? 2 : (category.equals("Shoes")) ? 3 : 4;
            ClothingItem.Category.Clothes oldOutfit = new ClothingItem.Category.Clothes(clothingName, clothingImg, clothingSubCategory, clothingColor, clothingStyle, clothingWeather, clothingCategory);
            myact.updateToDatabase(myact.getRef(), clothes[catPos], newOutfit, oldOutfit, imageUri);

        });

        imageButton.setOnClickListener(view -> {
            askPermissions();
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);

            startActivityForResult(intent, 100);
        });

        Button deleteBtn = myview.findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(v -> {
            ClothingItem.Category.Clothes oldOutfit = new ClothingItem.Category.Clothes(clothingName, clothingImg, clothingSubCategory, clothingColor, clothingStyle, clothingWeather, clothingCategory);
            myact.deleteFromDatabase(myact.getRef(), oldOutfit, false);

        });
        return myview;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == -1 && null != data) {
            Uri selectedImage = data.getData();
            imageUri  = selectedImage;
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = this.getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            String imagePath = picturePath;
            cursor.close();

            imageButton.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }



    private void askPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

    }
}