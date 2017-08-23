package com.example.fliest.freshnews.widget;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.Utils.DeviceInfoUtil;
import com.example.fliest.freshnews.activity.ImageSaveActivity;

/**
 * Created by Fliest on 2017/5/25.
 */
public class ZoomImageView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, View.OnLongClickListener {
    private boolean mOnce = false;
    private boolean isShow = true;

    private float mInitScale;
    private float mMidScale;
    private float mMaxScale;

    private Matrix mScaleMatrix;
    private ScaleGestureDetector mScaleGestureDetector;

    private int mTouchSlop;
    private GestureDetector mGestureDetector;
    private ClickedListener mListener;

    private String imageUrl;

    private boolean isAutoScale = false;

    public ZoomImageView(Context context) {
        this(context, null);
    }


    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        super.setScaleType(ScaleType.MATRIX);//此处很关键，关乎到ZoomImageView在ViewPager中能否起作用

        mScaleMatrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        //setLongClickable(true);
        //setOnLongClickListener(this);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                float x = e.getX();
                float y = e.getY();

                if (isAutoScale) {
                    return true;
                }

                if (getScale() < mMidScale) {
                    postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                    isAutoScale = true;
                } else {
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                    isAutoScale = true;
                }
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (isShow) {
                    mListener.onClicked(true);
                    isShow = false;
                } else {
                    mListener.onClicked(false);
                    isShow = true;
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);

                mListener.onLongClicked();
            }
        });
    }



    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }


    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            int width = getWidth();
            int height = getHeight();


            Drawable d = getDrawable();
            int dw;
            int dh;
            if (d == null) {
                setDrawingCacheEnabled(true);
                Bitmap bitmap = getDrawingCache();
                dh = bitmap.getHeight();
                dw = bitmap.getWidth();
                setDrawingCacheEnabled(false);
            } else {
                dw = d.getIntrinsicWidth();
                dh = d.getIntrinsicHeight();
            }


            float scale = 1.0f;

            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }

            if (dw < width && dh > height) {
                scale = height * 1.0f / dh;
            }

            //此处为放大，放大比例取最小的
            if (dw < width && dh < height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            // 此处为缩小，缩小比例赢选取最大的，虽然数值上取min的，但是缩小比例是最大的
            //当scale小于1，则scale的值越小，缩小比例越大
            if (dw > width && dh > height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);

            }

            //设置缩放限制
            mInitScale = scale;
            mMidScale = scale * 2;
            mMaxScale = scale * 4;

            //将图片移至中心
            int dx = (width - dw) / 2;
            int dy = (height - dh) / 2;

            //为了能适配控件，进行头次缩放
            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }

    public float getScale() {
        float[] value = new float[9];
        mScaleMatrix.getValues(value);
        return value[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float currentScale = getScale();
        float nextScale = detector.getScaleFactor();

        if (getDrawable() == null) return true;

        if ((currentScale > mInitScale && nextScale < 1.0f) || (currentScale < mMaxScale && nextScale > 1.0f)) {
            //限制缩放的范围
            //上一个if判断的是当前的状态
            //下一个if判断的是下一个状态
            if (currentScale * nextScale > mMaxScale) {
                nextScale = mMaxScale / currentScale;
            }

            if (currentScale * nextScale < mInitScale) {
                nextScale = mInitScale / currentScale;
            }

            mScaleMatrix.postScale(nextScale, nextScale, detector.getFocusX(), detector.getFocusY());

            checkBorderAndCenter();


            setImageMatrix(mScaleMatrix);
        }


        return true;
    }

    public void checkBorder() {
        RectF rectf = getMatrixRectF();

        float dx = 0;
        float dy = 0;

        int width = getWidth();
        int height = getHeight();

        //宽或高还大于控件的尺寸，则说明还在缩放
        //此处要保证图片的left和top和
        if (rectf.width() >= width) {
            if (rectf.left > 0) {
                dx = -rectf.left;
            }
            if (rectf.right < width) {
                dx = width - rectf.right;
            }
        }
        if (rectf.height() >= height) {
            if (rectf.top > 0) {
                dy = -rectf.top;
            }
            if (rectf.bottom < height) {
                dy = height - rectf.bottom;
            }
        }

        mScaleMatrix.postTranslate(dx, dy);
    }

    private void checkBorderAndCenter() {

        //checkBorder();
        RectF rectf = getMatrixRectF();

        float dx = 0;
        float dy = 0;

        int width = getWidth();
        int height = getHeight();

        //宽或高还大于控件的尺寸，则说明还在缩放
        //此处要保证图片的left和top和
        if (rectf.width() >= width) {
            if (rectf.left > 0) {
                dx = -rectf.left;
            }
            if (rectf.right < width) {
                dx = width - rectf.right;
            }
        }
        if (rectf.height() >= height) {
            if (rectf.top > 0) {
                dy = -rectf.top;
            }
            if (rectf.bottom < height) {
                dy = height - rectf.bottom;
            }
        }

        //宽或高如果小于控件的尺寸，则说明缩放已经停止，可以设置居中了
        if (rectf.width() < width) {
            dx = width / 2 - rectf.left - rectf.width() / 2;
        }

        if (rectf.height() < height) {
            dy = height / 2 - rectf.top - rectf.height() / 2;
        }

        mScaleMatrix.postTranslate(dx, dy);
    }

    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectf = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            //给rect设值这一步不可省略，如果省略，则two中获取的高和宽=0
            rectf.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            //System.out.println("one:"+rectf.toString());
            matrix.mapRect(rectf);
            // System.out.println("two:"+rectf.toString());
        }
        return rectf;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    private float mLastX;
    private float mLastY;

    private int mLastPointerCount;

    private long[] array = new long[]{0, 0};

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);


        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }


        mScaleGestureDetector.onTouchEvent(event);

        float x = 0;
        float y = 0;
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x = x / pointerCount;
        y = y / pointerCount;

        if (pointerCount != mLastPointerCount) {
            mLastX = x;
            mLastY = y;
        }

        /*
        *  移动的原理：
        *  记录上次的位置
        *  记录本次的位置
        *  得到本次移动的距离
        *
        *  防止出现瞬移的原理：
        *  如果在本次移动前，更新上次的位置，使之等于本次的位置，那么
        *  本次不会移动
        * */

        /**
         *  当触控点的数量发生变化时，那么触控点的中心点就会发生变化，则图片会根据中心点的变化
         *  而变化，会产生瞬间移动的现象，
         *  我们为了避免出现这一现象，给用户更好的体验，在触控点数量变化的那一刻，判断数量是否
         *  发生了变化，
         *
         */

        /*
        * 我们在每次移动图片的时候，触控点的数量都是由0 -->1或其他大于0的数值
        * 那么这就造成一个情况，在刚刚触摸的时候，我们的手指还没有移动，图片就移动了
        *
        * 这是因为，在本次移动前，Last就已经有了数值
        * */


        mLastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    if (getParent() instanceof ViewParent) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    if (getParent() instanceof ViewParent) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (isMoveAction(dx, dy)) {
                    if (getDrawable() != null) {
                        if (rectF.width() <= getWidth()) {
                            dx = 0;
                        }
                        if (rectF.height() <= getHeight()) {
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);

                        checkBorder();
                        setImageMatrix(mScaleMatrix);
                    }
                }

                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;

            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        return false;
    }

    public boolean isMoveAction(float dx, float dy) {

        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }

    @Override
    public boolean onLongClick(View v) {
        System.out.println("fel ong click");
        return true;
    }

    private class AutoScaleRunnable implements Runnable {
        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;

        private float mTargetScale;
        private float tempScale;

        private float x;
        private float y;

        public AutoScaleRunnable(float targetScale, float x, float y) {
            mTargetScale = targetScale;

            this.x = x;
            this.y = y;

            if (getScale() < targetScale) {
                tempScale = BIGGER;
            }

            if (getScale() > targetScale) {
                tempScale = SMALL;
            }
        }

        @Override
        public void run() {

            mScaleMatrix.postScale(tempScale, tempScale, x, y);
            checkBorderAndCenter();
            setImageMatrix(mScaleMatrix);

            float currentScale = getScale();

            if (((currentScale < mTargetScale) && (tempScale > 1.0f)) ||
                    ((currentScale > mTargetScale) && (tempScale < 1.0f))) {
                postDelayed(this, 16);
            } else {//达到我们的目标值，或者比目标值大

                float scale = mTargetScale / getScale();

                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenter();
                setImageMatrix(mScaleMatrix);

                isAutoScale = false;

            }
        }
    }

    public void setImageSrc(String url){
        imageUrl = url;
    }

    public interface ClickedListener {
        void onClicked(boolean isShow);
        void onLongClicked();
    }

    public void setClickedListener(ClickedListener listener) {
        mListener = listener;
    }
}
