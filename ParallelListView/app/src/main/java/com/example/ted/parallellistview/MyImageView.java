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
    private int MAXIMUM_SHIFT = 100;

    public static class ChangeYEvent {
        public boolean isScrollUp() {
            return isScrollUp;
        }

        boolean isScrollUp;
        int firstItem;
        int lastItem;

        public ChangeYEvent(boolean isScrollUp, int firstItem, int lastItem) {
            this.isScrollUp = isScrollUp;
            this.firstItem = firstItem;
            this.lastItem = lastItem;
        }
    }

    private int mExpandHeight = 400;//default value

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(1000, 200);
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
        super.onDraw(canvas);

    }

    int precount = 0;

    public void setExpandHeight(int mExpandHeight) {
        this.mExpandHeight = mExpandHeight;
    }

    private int mDeltaHeight = 0;
    private int mDefaultOffset = 0;

    public void onEvent(ChangeYEvent event) {
//        Log.d("Ted","mImage height "+getDrawable().getBounds().bottom);
//        int[] location = new int[2];
////
//        getLocationInWindow(location);
////        Log.d("Ted","location "+location[1]);
//        setScrollY((int) (location[1] * 0.2));


        if (event.isScrollUp) {
            doScrollUp(1);
        } else {
            doScrollDown(1);
        }

    }

    private void doScrollDown(int delta) {
        if (mDeltaHeight == 0) {
            mDeltaHeight = Math.abs(getDrawable().getBounds().bottom - getHeight());
        }
        if (getScrollY() < mDeltaHeight) {
            setScrollY(getScrollY() + delta);
        }
    }

    private void doScrollUp(int delta) {
        if (getScrollY() > 0) {
            setScrollY(getScrollY() - delta);
        }
    }
}
