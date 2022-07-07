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
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamf.AddOutfitFrag;
import com.example.teamf.ClothingItem;
import com.example.teamf.DisplayFavAdapter;
import com.example.teamf.MainActivity;
import com.example.teamf.OutfitItem;
import com.example.teamf.OutfitItemListAdapter;
import com.example.teamf.OutfitListener;
import com.example.teamf.OutfitRecyclerViewHolder;
import com.example.teamf.R;
import com.example.teamf.Search;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

public class FavFrag extends Fragment {
    private MainActivity myact;
    private FloatingActionButton fab;
    private Search filter;
    private RecyclerView recyclerView;
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
    private List<String> filter_values = new ArrayList<String>();
    private List<String> temp_filter_values = new ArrayList<String>();
    private ArrayList<OutfitItem> fav_outfits = new ArrayList<>();
    private OutfitListener listener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.frag_fav, container, false);
        myact = (MainActivity) getActivity();
        isFahrenheit = myact.getFahrenheit();
        Toolbar toolbar = (Toolbar) myact.findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar.findViewById(R.id.backButton).setVisibility(View.INVISIBLE);
        mTitle.setText(R.string.title_fav);


        NavigationBarView navBar = getActivity().findViewById(R.id.bottomNavigationView);
        navBar.setVisibility(View.VISIBLE);

        Search search = new Search();

        fav_outfits = myact.getFavorites();
        setRecyclerView(myview, fav_outfits);

        fab = myview.findViewById(R.id.fav_fab);
        fab.setOnClickListener(view -> {
            Fragment addOutfitFrag = new AddOutfitFrag();
            FragmentTransaction transaction = myact.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, addOutfitFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        });

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

                listener.setClickable(false);

                if (filter_clicked) {
                    listener.setClickable(true);

                    setRecyclerView(myview, fav_outfits);
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
                listener.setClickable(true);

                if (temp_filter_values.size() == 0) {
                    setRecyclerView(myview, fav_outfits);
                } else {
                    ArrayList<OutfitItem> filter_outfits = new ArrayList<>();
                    for (int i = 0; i < temp_filter_values.size(); i++) {
                        for (int j = 0; j < fav_outfits.size(); j++) {
                            OutfitItem outfit = fav_outfits.get(j);
                            if ((outfit.getStyle().equals(temp_filter_values.get(i)) || outfit.getWeather().equals(temp_filter_values.get(i))) && !filter_outfits.contains(outfit)) {
                                filter_outfits.add(outfit);
                            }
                        }
                    }
                    setRecyclerView(myview, filter_outfits);
                }
                temp_filter_values.clear();

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

        setRecyclerView(myview, fav_outfits);

        return myview;
    }

    public void setRecyclerView(View myview, ArrayList<OutfitItem> favorite_outfits) {
        recyclerView = myview.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(myview.getContext(), 1));
        listener = new OutfitListener(myact);
        recyclerView.setAdapter(new DisplayFavAdapter(favorite_outfits, listener));
    }

}