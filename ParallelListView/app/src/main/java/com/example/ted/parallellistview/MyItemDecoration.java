package com.example.ted.parallellistview;

import android.view.View;

import com.ted.scrollimageRecyclerview.ShiftItemDecoration;
import com.ted.scrollimageRecyclerview.onShiftListener;

/**
 * Created by ted on 14/12/18.
 */
public class MyItemDecoration extends ShiftItemDecoration {
    @Override
    public onShiftListener getShiftListener(View view) {
        return (onShiftListener)view.findViewById(R.id.img);
    }
}
