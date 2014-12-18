package com.ted.scrollimageRecyclerview;

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

import com.example.ted.parallellistview.BuildConfig;

/**
 * Created by ted on 14/12/9.
 */
public class ShiftImageView extends ImageView implements onShiftListener {
    private int mScreenHeight;
    private int mScreenWidth;

    private int mVisualHeight = 300;
    private int mVisualWidth = 1080;

    //移動的速度
    private float mShiftSpeed = 0.3f;

    //一定要小於0 圖片才會往上移動
    private float mBaseOffset;
    private float mMaximumShift;
    private float mMaximumWidht;

    public void setmVisualHeight(int mVisualHeight) {
        this.mVisualHeight = mVisualHeight;
    }

    public void setmVisualWidth(int mVisualWidth) {
        this.mVisualWidth = mVisualWidth;
    }

    public void setmShiftSpeed(float mShiftSpeed) {
        this.mShiftSpeed = mShiftSpeed;
    }

    public void setmBaseOffset(float mBaseOffset) {
        this.mBaseOffset = mBaseOffset;
    }

    public ShiftImageView(Context context) {
        super(context);
        init();
    }

    public ShiftImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShiftImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        if(getWidth() != 0 && getHeight() !=0) {
            if (getDrawable() != null) {

                //setDefault offset
                mBaseOffset = 0;

                //將圖片設定為橫幅滿版
                scaleImageToFull();

                //計算最大可移動高度
                caluculateMaximum();

            }
        }
        Log.d(getClass().getName(), "onMeausre width" + MeasureSpec.getSize(widthMeasureSpec) + "onMeausre height" + MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mVisualHeight);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mShiftOffset = mBaseOffset;
    }

    private void scaleImageToFull() {
        Log.d("Ted", "scaleImageToFull " + mBaseOffset + (float) getWidth() / (float) getDrawable().getIntrinsicWidth());
        float[] f = new float[9];
        getImageMatrix().getValues(f);
        f[Matrix.MTRANS_X] = 0;
        f[Matrix.MSCALE_X] = (float) getWidth() / (float) getDrawable().getIntrinsicWidth();
        f[Matrix.MSCALE_Y] = (float) getWidth() / (float) getDrawable().getIntrinsicWidth();
        f[Matrix.MTRANS_Y] = mBaseOffset;
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
        if (BuildConfig.DEBUG) {
            printMatrix();
        }

        super.onDraw(canvas);

        
        shiftImage();
    }

    private void printMatrix() {
        float[] f = new float[9];
        getImageMatrix().getValues(f);
        for (int i = 0; i < f.length; i++) {
            Log.d(getClass().getName(), "matrix value " + f[i]);
        }
    }

    private void shiftImage() {
        Log.d(getClass().getName(), "shiftImage " + mShiftOffset);
        float[] values = new float[9];
        getImageMatrix().getValues(values);
        values[Matrix.MTRANS_X] = 0;
        values[Matrix.MTRANS_Y] = mShiftOffset * mShiftSpeed;
        Matrix m = getImageMatrix();
        m.setValues(values);
        setImageMatrix(m);
    }

    @Override
    public void setShiftOffset(int dy) {
        //使圖片移動的時間點在整個畫面的中間
        double distance = dy - mScreenHeight / 2;

        //圖片出現時應該確保是在正確的位置
        checkPosition(distance);
        Log.d(getClass().getName(), "distance " + distance + "-mMaximumShift " + mMaximumShift);

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
