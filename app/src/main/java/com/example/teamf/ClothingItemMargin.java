package com.example.teamf;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ClothingItemMargin extends RecyclerView.ItemDecoration {

    private final int extraMargin;

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.right = extraMargin;
        outRect.left = extraMargin;
    }

    public ClothingItemMargin(Context context) {
        extraMargin = 10;
    }
}


