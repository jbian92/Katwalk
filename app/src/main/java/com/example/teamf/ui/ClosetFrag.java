package com.example.teamf.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamf.AddItemFrag;
import com.example.teamf.ClothingItemMargin;
import com.example.teamf.MainActivity;
import com.example.teamf.R;
import com.example.teamf.ItemListAdapter;
import com.example.teamf.Search;
import com.example.teamf.ClothingItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClosetFrag extends Fragment implements ItemListAdapter.OnItemClickListener {
    private MainActivity myact;
    private Search search;
    private Search filter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private boolean isFahrenheit;
    private boolean filter_clicked;
    private boolean weather_dropdown_clicked;
    private boolean style_dropdown_clicked;
    private boolean casual_clicked;
    private boolean business_clicked;
    private boolean formal_clicked;
    private boolean leq_30_clicked;
    private boolean from_31_to_40_clicked;
    private boolean from_41_to_50_box_clicked;
    private boolean from_51_to_60_clicked;
    private boolean from_61_to_70_clicked;
    private boolean from_71_to_80_clicked;
    private boolean from_81_to_90_clicked;
    private boolean geq_90_box_clicked;
    private List<String> temp_filter_values = new ArrayList<String>();
    private boolean clickable = true;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.frag_closet, container, false);
        myact = (MainActivity) getActivity();
        search = new Search();
        Toolbar toolbar = (Toolbar) myact.findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.findViewById(R.id.backButton).setVisibility(View.INVISIBLE);
        mTitle.setText(R.string.title_closet);

        NavigationBarView navBar = getActivity().findViewById(R.id.bottomNavigationView);
        navBar.setVisibility(View.VISIBLE);

        Button filter = (Button) myview.findViewById(R.id.filter_button);
        Button apply = (Button) myview.findViewById(R.id.apply_button);
        ImageView filterBackground = (ImageView) myview.findViewById(R.id.imageView);
        TextView filter_header = (TextView) myview.findViewById(R.id.filter_by_header_text);
        TextView style_header = (TextView) myview.findViewById(R.id.style_header_text);
        TextView weather_header = (TextView) myview.findViewById(R.id.weather_header_text);
        ImageButton style_dropdown = (ImageButton) myview.findViewById(R.id.style_dropdown);
        ImageButton weather_dropdown = (ImageButton) myview.findViewById(R.id.weather_dropdown);
        TextView style_types = (TextView) myview.findViewById(R.id.style_text);
        TextView weather_types = (TextView) myview.findViewById(R.id.weather_text);
        if (!isFahrenheit) {
            weather_types.setText("<0\u2103\n0-5\u2103\n6-10\u2103\n11-15\u2103\n16-20\u2103\n21-25\u2103\n26-30\u2103\n>30\u2103");
        }
        ImageButton casual_box = (ImageButton) myview.findViewById(R.id.casual_box_unchecked);
        ImageButton business_box = (ImageButton) myview.findViewById(R.id.business_box_unchecked);
        ImageButton formal_box = (ImageButton) myview.findViewById(R.id.form_box_unchecked);
        ImageButton leq_30_box = (ImageButton) myview.findViewById(R.id.leq_30_unchecked);
        ImageButton from_31_to_40_box = (ImageButton) myview.findViewById(R.id.from_31_to_40_box_unchecked);
        ImageButton from_41_to_50_box  = (ImageButton) myview.findViewById(R.id.from_41_to_50_box_unchecked);
        ImageButton from_51_to_60_box = (ImageButton) myview.findViewById(R.id.from_51_to_50_box_unchecked);
        ImageButton from_61_to_70_box = (ImageButton) myview.findViewById(R.id.from_61_to_70_box_unchecked);
        ImageButton from_71_to_80_box = (ImageButton) myview.findViewById(R.id.from_71_to_80_box_unchecked);
        ImageButton from_81_to_90_box = (ImageButton) myview.findViewById(R.id.from_80_to_90_box_unchecked);
        ImageButton geq_90_box = (ImageButton) myview.findViewById(R.id.more_than_90_box_unchecked);

        Drawable checkbox = getResources().getDrawable(R.drawable.checked_box);
        Drawable emptybox = getResources().getDrawable(R.drawable.add_item_border);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;

                clickable = false; // make recycler view items not clickable

                if (filter_clicked) {
                    clickable = true;

                    setRecyclerView(myview, search.getAllClothing(myact));
                    style_dropdown_clicked = false;
                    casual_clicked = false;
                    formal_clicked = false;
                    business_clicked = false;
                    visibility = view.INVISIBLE;
                    style_types.setVisibility(view.INVISIBLE);
                    weather_types.setVisibility(view.INVISIBLE);
                    casual_box.setVisibility(view.INVISIBLE);
                    business_box.setVisibility(view.INVISIBLE);
                    formal_box.setVisibility(view.INVISIBLE);
                    leq_30_box.setVisibility(view.INVISIBLE);
                    from_31_to_40_box.setVisibility(view.INVISIBLE);
                    from_41_to_50_box.setVisibility(view.INVISIBLE);
                    from_51_to_60_box.setVisibility(view.INVISIBLE);
                    from_61_to_70_box.setVisibility(view.INVISIBLE);
                    from_71_to_80_box.setVisibility(view.INVISIBLE);
                    from_81_to_90_box.setVisibility(view.INVISIBLE);
                    geq_90_box.setVisibility(view.INVISIBLE);
                    geq_90_box.setBackground(emptybox);
                    casual_box.setBackground(emptybox);
                    formal_box.setBackground(emptybox);
                    business_box.setBackground(emptybox);
                    leq_30_box.setBackground(emptybox);
                    from_31_to_40_box.setBackground(emptybox);
                    from_41_to_50_box.setBackground(emptybox);
                    from_51_to_60_box.setBackground(emptybox);
                    from_61_to_70_box.setBackground(emptybox);
                    from_71_to_80_box.setBackground(emptybox);
                    from_81_to_90_box.setBackground(emptybox);
                    geq_90_box.setBackground(emptybox);
                } else {
                    visibility = view.VISIBLE;
                }
                filterBackground.setVisibility(visibility);
                filter_header.setVisibility(visibility);
                style_header.setVisibility(visibility);
                weather_header.setVisibility(visibility);
                style_dropdown.setVisibility(visibility);
                weather_dropdown.setVisibility(visibility);
                apply.setVisibility(visibility);
                filter_clicked=!filter_clicked;
            }
        });

        style_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;

                if (style_dropdown_clicked) {
                    visibility = view.INVISIBLE;
                } else {
                    visibility = view.VISIBLE;
                }
                style_types.setVisibility(visibility);
                casual_box.setVisibility(visibility);
                business_box.setVisibility(visibility);
                formal_box.setVisibility(visibility);
                style_dropdown_clicked=!style_dropdown_clicked;
            }
        });

        weather_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;

                if (weather_dropdown_clicked) {
                    visibility = view.INVISIBLE;
                } else {
                    visibility = view.VISIBLE;
                }
                weather_types.setVisibility(visibility);
                leq_30_box.setVisibility(visibility);
                from_31_to_40_box.setVisibility(visibility);
                from_41_to_50_box.setVisibility(visibility);
                from_51_to_60_box.setVisibility(visibility);
                from_61_to_70_box.setVisibility(visibility);
                from_71_to_80_box.setVisibility(visibility);
                from_81_to_90_box.setVisibility(visibility);
                geq_90_box.setVisibility(visibility);
                weather_dropdown_clicked=!weather_dropdown_clicked;
            }
        });


        casual_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!casual_clicked) {
                    casual_box.setBackground(checkbox);
                    temp_filter_values.add("Casual");
                } else {
                    casual_box.setBackground(emptybox);
                    temp_filter_values.remove("Casual");
                }
                casual_clicked=!casual_clicked;
            }
        });

        formal_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!formal_clicked) {
                    formal_box.setBackground(checkbox);
                    temp_filter_values.add("Formal");
                } else {
                    formal_box.setBackground(emptybox);
                    temp_filter_values.remove("Formal");
                }
                formal_clicked=!formal_clicked;
            }
        });

        business_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!business_clicked) {
                    business_box.setBackground(checkbox);
                    temp_filter_values.add("Business");
                } else {
                    business_box.setBackground(emptybox);
                    temp_filter_values.remove("Business");
                }
                business_clicked=!business_clicked;
            }
        });

        leq_30_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!leq_30_clicked) {
                    leq_30_box.setBackground(checkbox);
                    if (isFahrenheit) {
                        temp_filter_values.add("<=30°F");
                    } else {
                        temp_filter_values.add("<=30°F");
                    }
                } else {
                    leq_30_box.setBackground(emptybox);
                    if (isFahrenheit) {
                        temp_filter_values.remove("<=30°F");
                    } else {
                        temp_filter_values.remove("<=30°F");
                    }
                }
                leq_30_clicked=!leq_30_clicked;
            }
        });


        from_31_to_40_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!from_31_to_40_clicked) {
                    from_31_to_40_box.setBackground(checkbox);
                    if (isFahrenheit) {
                        temp_filter_values.add("31-40°F");
                    } else {
                        temp_filter_values.add("31-40°F");
                    }
                } else {
                    from_31_to_40_box.setBackground(emptybox);
                    if (isFahrenheit) {
                        temp_filter_values.remove("31-40°F");
                    } else {
                        temp_filter_values.remove("31-40°F");
                    }
                }
                from_31_to_40_clicked=!from_31_to_40_clicked;
            }
        });

        from_41_to_50_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!from_41_to_50_box_clicked) {
                    from_41_to_50_box.setBackground(checkbox);
                    if (isFahrenheit) {
                        temp_filter_values.add("41-50°F");
                    } else {
                        temp_filter_values.add("41-50°F");
                    }
                } else {
                    from_41_to_50_box.setBackground(emptybox);
                    if (isFahrenheit) {
                        temp_filter_values.remove("41-50°F");
                    } else {
                        temp_filter_values.remove("41-50°F");
                    }
                }
                from_41_to_50_box_clicked=!from_41_to_50_box_clicked;
            }
        });

        from_51_to_60_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!from_51_to_60_clicked) {
                    from_51_to_60_box.setBackground(checkbox);
                    if (isFahrenheit) {
                        temp_filter_values.add("51-60°F");
                    } else {
                        temp_filter_values.add("51-60°F");
                    }
                } else {
                    from_51_to_60_box.setBackground(emptybox);
                    if (isFahrenheit) {
                        temp_filter_values.remove("51-60°F");
                    } else {
                        temp_filter_values.remove("51-60°F");
                    }
                }
                from_51_to_60_clicked=!from_51_to_60_clicked;
            }
        });

        from_61_to_70_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!from_61_to_70_clicked) {
                    from_61_to_70_box.setBackground(checkbox);
                    if (isFahrenheit) {
                        temp_filter_values.add("61-70°F");
                    } else {
                        temp_filter_values.add("61-70°F");
                    }
                } else {
                    from_61_to_70_box.setBackground(emptybox);
                    if (isFahrenheit) {
                        temp_filter_values.remove("61-70°F");
                    } else {
                        temp_filter_values.remove("61-70°F");
                    }
                }
                from_61_to_70_clicked=!from_61_to_70_clicked;
            }
        });

        from_71_to_80_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!from_71_to_80_clicked) {
                    from_71_to_80_box.setBackground(checkbox);
                    if (isFahrenheit) {
                        temp_filter_values.add("71-80°F");
                    } else {
                        temp_filter_values.add("71-80°F");
                    }
                } else {
                    from_71_to_80_box.setBackground(emptybox);
                    if (isFahrenheit) {
                        temp_filter_values.remove("71-80°F");
                    } else {
                        temp_filter_values.remove("71-80°F");
                    }
                }
                from_71_to_80_clicked=!from_71_to_80_clicked;
            }
        });

        from_81_to_90_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!from_81_to_90_clicked) {
                    from_81_to_90_box.setBackground(checkbox);
                    if (isFahrenheit) {
                        temp_filter_values.add("81-90°F");
                    } else {
                        temp_filter_values.add("81-90°F");
                    }
                } else {
                    from_81_to_90_box.setBackground(emptybox);
                    if (isFahrenheit) {
                        temp_filter_values.remove("81-90°F");
                    } else {
                        temp_filter_values.remove("81-90°F");
                    }
                }
                from_81_to_90_clicked=!from_81_to_90_clicked;
            }
        });

        geq_90_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = view.INVISIBLE;
                if (!geq_90_box_clicked) {
                    geq_90_box.setBackground(checkbox);
                    if (isFahrenheit) {
                        temp_filter_values.add(">90°F");
                    } else {
                        temp_filter_values.add(">90°F");
                    }
                } else {
                    geq_90_box.setBackground(emptybox);
                    if (isFahrenheit) {
                        temp_filter_values.remove(">90°F");
                    } else {
                        temp_filter_values.remove(">90°F");
                    }
                }
                geq_90_box_clicked=!geq_90_box_clicked;
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickable = true;
                if (temp_filter_values.size() == 0) {
                    setRecyclerView(myview, search.getAllClothing(myact));
                } else {
                    for (int i = 0; i < temp_filter_values.size(); i++) {
                        setRecyclerView(myview, search.filter(myact, temp_filter_values.get(i), temp_filter_values.get(i), temp_filter_values.get(i), temp_filter_values.get(i)));
                        temp_filter_values.remove(i);
                    }
                }


                style_dropdown_clicked = false;
                casual_clicked = false;
                formal_clicked = false;
                business_clicked = false;
                filter_clicked = false;
                weather_dropdown_clicked = false;
                leq_30_clicked = false;
                from_31_to_40_clicked = false;
                from_41_to_50_box_clicked = false;
                from_51_to_60_clicked = false;
                from_61_to_70_clicked = false;
                from_71_to_80_clicked = false;
                from_81_to_90_clicked = false;
                geq_90_box_clicked = false;
                apply.setVisibility(view.INVISIBLE);
                filter_header.setVisibility(view.INVISIBLE);
                filterBackground.setVisibility(view.INVISIBLE);
                style_header.setVisibility(view.INVISIBLE);
                style_dropdown.setVisibility(view.INVISIBLE);
                weather_dropdown.setVisibility(view.INVISIBLE);
                weather_header.setVisibility(view.INVISIBLE);
                style_types.setVisibility(view.INVISIBLE);
                weather_types.setVisibility(view.INVISIBLE);
                casual_box.setVisibility(view.INVISIBLE);
                business_box.setVisibility(view.INVISIBLE);
                formal_box.setVisibility(view.INVISIBLE);
                leq_30_box.setVisibility(view.INVISIBLE);
                from_31_to_40_box.setVisibility(view.INVISIBLE);
                from_41_to_50_box.setVisibility(view.INVISIBLE);
                from_51_to_60_box.setVisibility(view.INVISIBLE);
                from_61_to_70_box.setVisibility(view.INVISIBLE);
                from_71_to_80_box.setVisibility(view.INVISIBLE);
                from_81_to_90_box.setVisibility(view.INVISIBLE);
                geq_90_box.setVisibility(view.INVISIBLE);
                geq_90_box.setBackground(emptybox);
                casual_box.setBackground(emptybox);
                formal_box.setBackground(emptybox);
                business_box.setBackground(emptybox);
                leq_30_box.setBackground(emptybox);
                from_31_to_40_box.setBackground(emptybox);
                from_41_to_50_box.setBackground(emptybox);
                from_51_to_60_box.setBackground(emptybox);
                from_61_to_70_box.setBackground(emptybox);
                from_71_to_80_box.setBackground(emptybox);
                from_81_to_90_box.setBackground(emptybox);
                geq_90_box.setBackground(emptybox);
            }
        });




        //Create RecyclerView
        setRecyclerView(myview, search.getAllClothing(myact));

        fab = myview.findViewById(R.id.closet_fab);
        getParentFragmentManager().addOnBackStackChangedListener(
                () -> myact.getRef().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        myact.getAllClothes(dataSnapshot);
                        setRecyclerView(myview, search.getAllClothing(myact));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("Failed to read value.", databaseError.toException());

                    }

                })
        );

        fab.setOnClickListener(view -> {
            Fragment addItemFrag = new AddItemFrag();
            FragmentTransaction transaction = myact.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, addItemFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        });


        SearchView sv = (SearchView) myview.findViewById(R.id.search);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setRecyclerView(myview, search.search(myact, newText));
                return false;
            }
        });

        return myview;
    }



    public void setRecyclerView(View myview, ArrayList<ClothingItem.Category.Clothes> clothes) {
        recyclerView = myview.findViewById(R.id.recyclerview);
        //recyclerView.clear();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(myview.getContext(), 3));
        recyclerView.setAdapter(new ItemListAdapter(clothes, this));
        recyclerView.addItemDecoration(new ClothingItemMargin(getContext()));
    }

    @Override
    public void onItemClick(ClothingItem.Category.Clothes clothing) {
        if (clickable) {
            Bundle bundle = new Bundle();
            bundle.putString("name", clothing.getName());
            bundle.putString("category", clothing.getCategory());
            bundle.putString("subcategory", clothing.getSub());
            bundle.putString("color", clothing.getColor());
            bundle.putString("style", clothing.getStyle());
            bundle.putString("weather", clothing.getWeather());
            bundle.putString("pic", clothing.getPic());

            // Go to Item Details screen when user clicks on a clothing item
            Fragment itemDetailFrag = new ItemDetailFrag();
            itemDetailFrag.setArguments(bundle);
            FragmentTransaction transaction = myact.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, itemDetailFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}