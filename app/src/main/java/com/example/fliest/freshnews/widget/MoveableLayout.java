package com.example.fliest.freshnews.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Fliest on 2017/2/8.
 */
public class MoveableLayout extends LinearLayout {

    private ViewDragHelper mHelper;
    private View mImageView;
    private int mChildHeight;
    private Point originPos = new Point();


    public MoveableLayout(Context context) {
        this(context, null);
    }

    public MoveableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {

                return child == mImageView;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                /*int topBound = getPaddingTop();
                int bottomBound = getHeight() - mImageView.getHeight();
                int newTop = Math.min(Math.max(top,topBound),bottomBound);*/

                return top;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);

                int parentHeight = getHeight();
                int blankHeight = (parentHeight - mChildHeight) / 2;
                int bottom = mChildHeight + top;
                evaluate(top, blankHeight, bottom);

            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                int parentHeight = getHeight();
                int height = (parentHeight - mChildHeight) / 2;
                int childTop = mImageView.getTop();


                if (childTop < height / 2) {
                    System.out.println("finish1");
                    //mHelper.smoothSlideViewTo(mImageView, originPos.x, -mChildHeight);
                    //invalidate();

                    moveWithAnimation(-mChildHeight + 1);
                } else if (childTop > parentHeight / 2) {
                    System.out.println("finish2");
                    /*mHelper.smoothSlideViewTo(mImageView, originPos.x, parentHeight + mChildHeight);
                    invalidate();*/

                    moveWithAnimation(parentHeight);
                } else {
                    if (releasedChild == mImageView) {
                        mHelper.settleCapturedViewAt(originPos.x, originPos.y);
                        invalidate();
                    }
                }
            }
        });
    }


    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mImageView = getChildAt(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mChildHeight = mImageView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        originPos.x = mImageView.getLeft();
        originPos.y = mImageView.getTop();
    }

    public OnImageViewDismissListener mListener;

    public interface OnImageViewDismissListener {
        void onImageViewDismiss();
    }

    public void setOnImageViewDismissListener(OnImageViewDismissListener onImageViewDismissListener) {
        mListener = onImageViewDismissListener;
    }

    private void evaluate(int currentTop, int blankHeight, int currenBottom) {

        int childAndBlankHeight = blankHeight + mChildHeight;
        int upBoundHeight = childAndBlankHeight;
        int downBoundHeight = blankHeight;

        int color = 255;
        if (currenBottom < upBoundHeight) {

            color = ((currenBottom) * 255) / (upBoundHeight);


        } else if (currentTop > downBoundHeight) {

            int top = currentTop - blankHeight;
            color = 255 - ((top) * 255) / (childAndBlankHeight);

        }

        if (color >= 0 && color <= 255) {
            setBackgroundColor(Color.argb(color, 0, 0, 0));
        }

    }

    private void moveWithAnimation(int dy) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mImageView, "Y", dy);
        objectAnimator.setDuration(300);
        objectAnimator.start();
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int blankHeight = (getHeight() - mChildHeight) / 2;
                evaluate((int) mImageView.getY(),blankHeight, (int) (mImageView.getY()+mChildHeight));
            }
        });

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mListener.onImageViewDismiss();
            }
        });

    }

}
