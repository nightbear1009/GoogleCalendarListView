package com.example.ted.parallellistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import de.greenrobot.event.EventBus;

/**
 * Created by ted on 14/12/9.
 */
public class MyImageView extends ImageView {
    public static class ChangeYEvent {
        int y;
        int mFirst;
        int mLast;

        public ChangeYEvent(int y, int firstVisible, int lastVisible) {
            this.y = y;
            mFirst = firstVisible;
            mLast = lastVisible;
        }
    }

    private int y = 0;
    private int mItemPosition;
    private Paint paint = new Paint();

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
        Log.d("Ted", "onAttach");
    }


    //Not Trigger
    @Override
    protected void onDetachedFromWindow() {
        Log.d("Ted", "onDetach");
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

    public void setItemPosition(int pos) {
        mItemPosition = pos;
    }


    public void onEventMainThread(ChangeYEvent event) {
//        if(event.mFirst <mItemPosition && event.mLast > mItemPosition) {


        int[] location = new int[2];

        getLocationInWindow(location);

        setScrollY((int) (location[1] * 0.5));
    }
}
