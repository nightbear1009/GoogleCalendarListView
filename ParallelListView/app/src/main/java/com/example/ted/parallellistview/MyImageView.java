package com.example.ted.parallellistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import de.greenrobot.event.EventBus;

/**
 * Created by ted on 14/12/9.
 */
public class MyImageView extends ImageView {
    private int MAXIMUM_SHIFT = 100;

    public static class ChangeYEvent {
        public ChangeYEvent(int dy) {
            this.dy = dy;
        }

        private int dy;

        public int getDy() {
            return dy;
        }
    }

    private int mHeight;
    private int mWidth;

    private int mScreenHeight;

    public MyImageView(Context context) {
        super(context);
        init();
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth  = size.x;
        mHeight = size.y;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(mWidth, 300);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    //
    //Not Trigger
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(ChangeYEvent event) {
        if(event.getDy() >= mHeight/2 &&  event.getDy() <= mHeight/2 + 100){
            setScrollY((event.getDy() - mHeight/2)/3);
        }else if (event.getDy() <= mHeight/2 && event.getDy() >= mHeight/2 - 100){
            setScrollY((event.getDy() - mHeight/2)/3);

        }
    }
}
