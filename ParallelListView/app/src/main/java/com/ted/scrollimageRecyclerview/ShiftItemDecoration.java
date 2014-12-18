package com.ted.scrollimageRecyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ted on 14/12/17.
 */
public abstract class ShiftItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (parent.getChildPosition(child) % 20 == 0) {
                onShiftListener v = getShiftListener(child);
                if(v!=null) {
                    v.setShiftOffset(child.getTop());
                }
            }

        }
    }

    public abstract onShiftListener getShiftListener(View view);

}
