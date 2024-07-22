package com.example.poly_lib_su24;

import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
public class SpaceItem extends RecyclerView.ItemDecoration {
    private final int space;

    public SpaceItem(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = space;
    }
}
