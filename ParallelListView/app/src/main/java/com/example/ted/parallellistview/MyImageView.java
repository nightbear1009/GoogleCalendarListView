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
    private int mScreenHeight;
    private int mScreenWidth;

    int mVisualHeight = 300;
    int mVisualWidth = 1080;

    //一定要小於0 圖片才會往上移動
    float mBaseOffset;
    float mMaximumShift;
    float mMaximumWidht;

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
        mShiftOffset = mBaseOffset;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);

        if (getDrawable() != null) {
            //將圖片設定為橫幅滿版
            scaleImageToFull();

            //計算最大可移動高度
            caluculateMaximum();

            mBaseOffset = -getDrawable().getIntrinsicHeight()/3;
        }
    }

    private void scaleImageToFull(){
        float[] f = new float[9];
        getImageMatrix().getValues(f);
        f[Matrix.MTRANS_X] = 0;
        f[Matrix.MSCALE_X] = (float) getWidth() / (float) getDrawable().getIntrinsicWidth();
        f[Matrix.MSCALE_Y] = (float) getWidth() / (float) getDrawable().getIntrinsicWidth();
        f[Matrix.MTRANS_Y] = -mBaseOffset;
        mShiftOffset = f[Matrix.MTRANS_Y];
        Matrix m = getImageMatrix();
        m.setValues(f);
        setScaleType(ScaleType.MATRIX);
        setImageMatrix(m);
        requestLayout();
        invalidate();
    }

    private void caluculateMaximum() {
        float[] f = new float[9];
        getImageMatrix().getValues(f);

        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];
        final Drawable d = getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);
        mMaximumShift = -Math.abs(actH - mVisualHeight);
        mMaximumWidht = Math.abs(actH - mVisualWidth);
        Log.d(getClass().getName(), "MaxHeight " + mMaximumShift + "MaxWidth " + mMaximumWidht);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        shiftImage();
    }

    private void shiftImage() {
        float[] values = new float[9];
        getImageMatrix().getValues(values);
        values[Matrix.MTRANS_X] = 0;
        values[Matrix.MTRANS_Y] = mShiftOffset;
        Matrix m = getImageMatrix();
        m.setValues(values);
        setImageMatrix(m);
    }

    public void setShiftOffset(int dy) {
        //使圖片移動的時間點在整個畫面的中間
        double distance = dy - mScreenHeight / 2;

        //圖片出現時應該確保是在正確的位置
        checkPosition(distance);
        Log.d("Ted", "distance " + distance +"-mMaximumShift "+ mMaximumShift);

        //若沒有超出界限則設定shift 的offset
        if (distance < mBaseOffset && distance > mMaximumShift) {
            mShiftOffset = (float) distance;
            invalidate();
        }
    }

    private void checkPosition(double distance) {

        float[] f = new float[9];
        getImageMatrix().getValues(f);
        float imagePostion = f[Matrix.MTRANS_Y];
        if (distance < mMaximumShift && imagePostion != mMaximumShift) {
            mShiftOffset = (float) mMaximumShift;
        } else if (distance > mBaseOffset && imagePostion != mBaseOffset) {
            mShiftOffset = mBaseOffset;
        }

        invalidate();
    }

    private float mShiftOffset = 0;


}
