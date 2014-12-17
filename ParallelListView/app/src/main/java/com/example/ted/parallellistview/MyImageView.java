package com.example.ted.parallellistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by ted on 14/12/9.
 */
public class MyImageView extends ImageView {

    public static class ChangeYEvent {
        public ChangeYEvent(int dy) {
            this.dy = dy;
        }

        private int dy;

        public int getDy() {
            return dy;
        }
    }

    private int mScreenHeight;
    private int mScreenWidth;
    float mMaximumHeight ;
    float mMaximumWidht;
    int mVisualHeight = 300;
    int mVisualWidth =1080;

    Orientation mOrientation;
    enum Orientation{
        Vertical,
        Horizontal
    }

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
        mScreenWidth = size.x;
        mScreenHeight = size.y;



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(mVisualWidth, mVisualHeight);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        y=0;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);

        if(getDrawable()!=null) {
            float[] f = new float[9];
            getImageMatrix().getValues(f);
            Log.d("Ted","getHeight "+ getHeight() +" "+getDrawable().getIntrinsicHeight()+ " "+getWidth() +" "+getDrawable().getIntrinsicWidth());
            Log.d("Ted","sku "+f[Matrix.MSKEW_X]+ " "+f[Matrix.MSKEW_Y] +" "+f[Matrix.MPERSP_0]+" "+f[Matrix.MPERSP_1]+" "+f[Matrix.MPERSP_2]);
            f[Matrix.MTRANS_X] = 0;
            f[Matrix.MSCALE_X] = (float)getWidth() / (float)getDrawable().getIntrinsicWidth();
            f[Matrix.MSCALE_Y] = (float)getWidth() / (float)getDrawable().getIntrinsicWidth();
            Matrix m = getImageMatrix();
            m.setValues(f);
            setScaleType(ScaleType.MATRIX);
            setImageMatrix(m);
            requestLayout();
            invalidate();
            caluculate();

        }
    }

    private void caluculate(){
        float[] f = new float[9];
        getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];
        final Drawable d = getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);
        mMaximumHeight = Math.abs(actH - mVisualHeight);
        mMaximumWidht = Math.abs(actH - mVisualWidth);
        Log.d(getClass().getName(),"MaxHeight "+mMaximumHeight + "MaxWidth "+mMaximumWidht);

    }

    @Override
    protected void onDraw(Canvas canvas) {
Log.d("Ted","yyy "+y);
        Log.d("Ted","getPAdding"+getPaddingBottom()+" "+getPaddingTop());

        super.onDraw(canvas);
        float[] ff = new float[9];
        getImageMatrix().getValues(ff);
        ff[Matrix.MTRANS_X] = 0;
        ff[Matrix.MTRANS_Y] = y;
        Matrix m = getImageMatrix();
        m.setValues(ff);
        setImageMatrix(m);
        requestLayout();

//        if(getDrawable()!=null) {
//            float[] f = new float[9];
//            getImageMatrix().getValues(f);
//
//            // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
//            final float scaleX = f[Matrix.MSCALE_X];
//            final float scaleY = f[Matrix.MSCALE_Y];
//            final Drawable d = getDrawable();
//            final int origW = d.getIntrinsicWidth();
//            final int origH = d.getIntrinsicHeight();
//
//            // Calculate the actual dimensions
//            final int actW = Math.round(origW * scaleX);
//            final int actH = Math.round(origH * scaleY);
//            Log.e("DBG", "["+origW+","+origH+"] -> ["+actW+","+actH+"] & scales: x="+scaleX+" y="+scaleY);
//        }
    }

    public void setY(int dy){

        double distance =  dy - mScreenHeight/2;
        Log.d("Ted","distance "+distance);
        if(distance < 0 && distance > -mMaximumHeight){
            y = (float)distance;
            invalidate();
        }
//            invalidate();
//        setScrollY(y);
//        }else if (dy <= mScreenHeight/2 ){
//            int distance =  dy - mScreenHeight/2;
//            y = distance*0.2f;
//            invalidate();
//        }
    }

    private float y= 0;




}
