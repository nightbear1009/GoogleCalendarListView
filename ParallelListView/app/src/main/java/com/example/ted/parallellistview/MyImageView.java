package com.example.ted.parallellistview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import de.greenrobot.event.EventBus;

/**
 * Created by ted on 14/12/9.
 */
public class MyImageView extends ImageView {
    public static class ChangeYEvent {
        public ChangeYEvent() {}
    }

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }


    //Not Trigger
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() != null) {
            getDrawable().setBounds(0, 0, getWidth(), getHeight() + 400);
            getDrawable().draw(canvas);
        } else {
            super.onDraw(canvas);
        }
    }

    public void onEventMainThread(ChangeYEvent event) {
        int[] location = new int[2];

        getLocationInWindow(location);

        setScrollY((int) (location[1] * 0.5));
    }
}
