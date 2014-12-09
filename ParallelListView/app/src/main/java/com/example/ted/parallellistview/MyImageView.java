package com.example.ted.parallellistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import de.greenrobot.event.EventBus;

/**
 * Created by ted on 14/12/9.
 */
public class MyImageView extends ImageView {
    public static class ChangeYEvent{
        int y;

        public ChangeYEvent(int y) {
            this.y = y;
        }
    }
    private int y=0;
    private Bitmap mBitmap;
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

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setStyle(Paint.Style.FILL);
        if(Math.abs(mItemPosition -y) <=30){
            y = y - mItemPosition;
            if(y>0) {
                y = (30-y) * 3;
            }else{
                y = (30+y) *3;
            }
        }else{
            y = 0;
        }

        canvas.drawBitmap(mBitmap, 0, -200+y, paint);
    }


    @Override
    public void setImageBitmap(Bitmap bm) {
        mBitmap = bm;
    }

    public void setItemPosition(int pos){
        mItemPosition = pos;
    }

    public void setY(int _y){
        y = _y;
    }

    public void onEventMainThread(ChangeYEvent event){
        setY(event.y);
        invalidate();
    }
}
