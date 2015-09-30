package com.elegion.githubclient.adapter.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.elegion.githubclient.R;

/**
 * Created by vigursky on 28.09.2015.
 */
public class RepositoryDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int padding_medium = (int) parent.getResources().getDimension(R.dimen.padding_medium);
        outRect.set(padding_medium, padding_medium, padding_medium, padding_medium);
    }
}
