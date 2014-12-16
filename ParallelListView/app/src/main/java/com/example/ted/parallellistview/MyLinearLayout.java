package com.example.ted.parallellistview;

import android.content.Context;
import android.graphics.Point;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import de.greenrobot.event.EventBus;

/**
 * Created by tedliang on 14/12/10.
 */
public class MyLinearLayout extends RelativeLayout {
    private MyImageView mImg;

    public MyLinearLayout(Context context) {
        super(context);
        init();
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.mylinearlayout, this, true);
        mImg = (MyImageView) view.findViewById(R.id.imgview);
    }


    public ImageView getImageView() {

        return mImg;
    }

}
