package com.example.fliest.freshnews.other;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.example.fliest.freshnews.widget.SlidingLayout;

/**
 * Created by Fliest on 2017/8/4.
 */

public class SwipeBackPage {
    public Activity mActivity;
    private SlidingLayout mSlidingLayout;
    private boolean mSwipeEnable = true;

    public SwipeBackPage(Activity activity){
        mActivity = activity;
    }

    public void onCreate(){
        mSlidingLayout = new SlidingLayout(mActivity);
        if(mSwipeEnable){
            mSlidingLayout.bindActivity(mActivity);
        }

        FrontSlider frontSlider = new FrontSlider(this);
        mSlidingLayout.setSwipeBackListener(frontSlider);
    }

    public SlidingLayout getSlidingLayout(){
        return mSlidingLayout;
    }

    public SwipeBackPage setRequestDisallowInterceptTouchEvent(boolean disallowIntercept){
        mSlidingLayout.setRequestDisallowInterceptTouchEvent(disallowIntercept);
        return this;
    }

    public SwipeBackPage setSwipeBackEnable(boolean enable) {
        mSlidingLayout.setEnableGesture(enable);

        return this;
    }
}
