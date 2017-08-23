package com.example.fliest.freshnews.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.fliest.freshnews.Utils.DeviceInfoUtil;

/**
 * Created by Fliest on 2017/7/27.
 */

public class FourThreeImageView extends android.support.v7.widget.AppCompatImageView {
    private Context mContext;

    public FourThreeImageView(Context context) {
        this(context, null);
    }

    public FourThreeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FourThreeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = MeasureSpec.getSize(widthMeasureSpec) * 3 / 4;

        if (heightSpecMode != MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSpecSize, height);
        }*/

        int fourThreeHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) * 3 / 4,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, fourThreeHeight);
    }
}
