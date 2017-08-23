package com.example.fliest.freshnews.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.Utils.DeviceInfoUtil;

import java.io.InputStream;

/**
 * Created by Fliest on 2017/7/28.
 */

public class SwipeBackLayout extends FrameLayout {

    private View mChild;
    private ViewDragHelper mViewDragHelper;
    private Context mContext;
    private OnActivityCloseListener mListener;
    private Paint mPaint;
    private Rect mRect;
    private View mView;
    private BitmapDrawable mBitmapDrawable;
    private int mDeviceHeight;
    //private int mContentWidth;
    private View mContentView;
    private Activity mActivity;

    public SwipeBackLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        mDeviceHeight = DeviceInfoUtil.getDeviceHeight(context);

        init(context);


    }

    public void bindActivity(Activity activity) {
        mActivity = activity;
        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
        View child = decorView.getChildAt(0);
        decorView.removeView(child);
        addView(child);
        setContentView(child);
        decorView.addView(this);
    }

    private void setContentView(View view) {
        mContentView = view;
    }

    private void init(Context context) {

        InputStream in = getResources().openRawResource(R.drawable.shadow);
        mBitmapDrawable = new BitmapDrawable(in);

        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            private int mRightBound;
            private int mLeftBound;

            @Override
            public boolean tryCaptureView(View child, int pointerId) {

                System.out.println("child:"+child);
                System.out.println("mContentView:"+mContentView);

                return mContentView == child;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                mLeftBound = getPaddingLeft();
                mRightBound = getWidth();
                int newLeft = Math.min(Math.max(left, mLeftBound), mRightBound);

                return newLeft;
            }


            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);


            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                int middle = getWidth() / 2;
                int childLeft = mChild.getLeft();

                if (xvel > 1000) {
                    mViewDragHelper.smoothSlideViewTo(mChild, mRightBound, 0);
                    invalidate();

                }
                if (xvel < -1000) {
                    mViewDragHelper.smoothSlideViewTo(mChild, 0, mLeftBound);
                    invalidate();
                }
                if (xvel <= 1000 && xvel >= -1000) {
                    if (childLeft < middle) {
                        mViewDragHelper.smoothSlideViewTo(mChild, mLeftBound, 0);
                    } else if (childLeft > middle) {
                        mViewDragHelper.smoothSlideViewTo(mChild, mRightBound, 0);
                    }
                    invalidate();
                }
            }
        });
    }

    @Override
    public void computeScroll() {

        if (mViewDragHelper.continueSettling(true)) {
            invalidate();

        } else {

        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mChild = getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        System.out.println("onTouchEvent");
        System.out.println("mViewDragHelper.getViewDragState():"+mViewDragHelper.getViewDragState());

        invalidate();
        return true;
    }

    int mLastX = 0;
    int mLastY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    System.out.println("onInterceptTouchEvent  true");
                    return true;
                }
                break;
        }

        mLastX = x;
        mLastY = y;

        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean flag = super.drawChild(canvas, child, drawingTime);
        if (child == mChild) {
            drawShadow(canvas,child);
            drawAlpha(canvas, child);
        }

        return flag;
    }

    private void drawShadow(Canvas canvas,View child) {
        Rect rect = new Rect(child.getLeft() - 25, 0, child.getLeft(), mDeviceHeight);

        final Rect childRect = new Rect();
        //child.getHitRect(childRect);
        //mBitmapDrawable.setBounds(childRect.left - mBitmapDrawable.getIntrinsicWidth(), childRect.top,childRect.left, childRect.bottom);


        mBitmapDrawable.setBounds(rect);
        mBitmapDrawable.draw(canvas);
    }

    private void drawAlpha(Canvas canvas, View child) {
        int currentColor = 120 - child.getLeft() * 120 / getWidth();
        canvas.clipRect(0, 0, child.getLeft(), mDeviceHeight);
        canvas.drawColor(Color.argb(currentColor, 0, 0, 0));
    }

    public interface OnActivityCloseListener {
        void onActivityClose();
    }

    public void setOnActivityCloseListener(OnActivityCloseListener listener) {
        mListener = listener;
    }

    public void finishActivityByScroll() {
        mViewDragHelper.smoothSlideViewTo(mChild, DeviceInfoUtil.getDeviceWidth(mContext), 0);
        invalidate();
    }


}
