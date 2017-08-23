package com.example.fliest.freshnews.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.fliest.freshnews.R;

/**
 * Created by Fliest on 2017/7/15.
 */

public class BottomTabButton extends LinearLayout implements View.OnClickListener{

    private Drawable mDrawable;
    private String mText;
    private boolean isChecked = false;
    private OnChildCheckedListener mListener;
    private ImageView mImageView;
    private TextView mTextView;
    private Drawable mChickDrawable;
    private int mClickTextColor;
    private int mUnClickTextColor;

    public BottomTabButton(Context context) {
        this(context,null);
    }

    public BottomTabButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);


    }

    public BottomTabButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.BottomTabButton);

        mDrawable = ta.getDrawable(R.styleable.BottomTabButton_src);
        mText = ta.getString(R.styleable.BottomTabButton_text);

        mChickDrawable = ta.getDrawable(R.styleable.BottomTabButton_clickSrc);
        mClickTextColor = ta.getColor(R.styleable.BottomTabButton_clickTextColor,0);
        mUnClickTextColor = ta.getColor(R.styleable.BottomTabButton_unClickTextColor,0);


        ta.recycle();

        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater infaltaer = LayoutInflater.from(context);
        View view = infaltaer.inflate(R.layout.layout_bottom_tab_btn,this,true);

        mImageView = (ImageView) view.findViewById(R.id.imageview_bottom_tab_btn);
        mTextView = (TextView) view.findViewById(R.id.text_bottom_tab_btn);

        mImageView.setImageDrawable(mDrawable);
        mTextView.setText(mText);

        setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(mListener!=null){
            System.out.println("onClick");
            mListener.onChildChecked(this,v.getId());
        }

    }

    interface OnChildCheckedListener{
        void onChildChecked(BottomTabButton view,int id);
    }

    public void setOnChildCheckedListener(OnChildCheckedListener listener){
        mListener = listener;
    }

    public boolean isChecked(){
        return isChecked;
    }
    public void setChecked(boolean checked){
        isChecked = checked;
        if(checked){
            mImageView.setImageDrawable(mChickDrawable);
            mTextView.setTextColor(mClickTextColor);
        } else {
            mImageView.setImageDrawable(mDrawable);
            mTextView.setTextColor(mUnClickTextColor);
        }
    }
}
