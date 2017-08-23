package com.example.fliest.freshnews.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/**
 * Created by Fliest on 2017/7/15.
 */

public class TabButtonGroup extends LinearLayout implements View.OnClickListener, BottomTabButton.OnChildCheckedListener {
    private int checkedID = 0;
    private BottomTabButton checkedView = null;
    NoScrollViewPager mViewPager;
    OnCheckChangeListener mListener;

    public TabButtonGroup(Context context) {
        this(context, null);
    }

    public TabButtonGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButtonGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }



    @Override
    public void onClick(View v) {

    }


    @Override
    public void onChildChecked(BottomTabButton view, int id) {
        System.out.println("id :"+id);
        System.out.println("checkedID:"+checkedID);
        mListener.onCheckChange(id);

        if (checkedID != id) {
            if (checkedID == 0) {
                view.setChecked(true);
            } else {
                System.out.println("checkedID != 0");
                checkedView.setChecked(false);
                view.setChecked(true);

            }
            checkedView = view;
            checkedID = id;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        System.out.println("childCount:"+childCount);
        for (int i = 0; i < childCount; i++) {
            System.out.println("mLissssss");
            ((BottomTabButton) getChildAt(i)).setOnChildCheckedListener(this);
        }

        ((BottomTabButton) getChildAt(0)).setChecked(true);
        checkedID = getChildAt(0).getId();
        checkedView = ((BottomTabButton) getChildAt(0));
    }

    public void setViewPager(NoScrollViewPager viewPager){
        mViewPager = viewPager;
    }


    public interface OnCheckChangeListener{
        void onCheckChange(int id);
    }

    public void setOnCheckChangeListener(OnCheckChangeListener listener){
        mListener = listener;
    }
}
