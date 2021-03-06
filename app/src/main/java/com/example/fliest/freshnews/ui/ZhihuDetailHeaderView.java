package com.example.fliest.freshnews.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.Utils.DeviceInfoUtil;
import com.example.fliest.freshnews.activity.ImageShowActivity;
import com.example.fliest.freshnews.bean.topnews.TopnewsDetailBean;
import com.example.fliest.freshnews.bean.zhihu.ZhihuDetailStroyBean;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fliest on 2017/8/3.
 */

public class ZhihuDetailHeaderView extends FrameLayout {

    @BindView(R.id.image_zhihu_detail)
    ImageView mImageView;
    @BindView(R.id.text_zhihu_detail)
    TextView mTextView;
    @BindView(R.id.htmltext_zhihu_detail)
    HtmlTextView mHtmlTextView;

    private Context mContext;
    private String mImageSrc;

    public ZhihuDetailHeaderView(@NonNull Context context) {
        this(context,null);
    }

    public ZhihuDetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZhihuDetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        initView();
    }

    private void initView() {
        inflate(mContext,R.layout.layout_item_zhihui_detail,this);

        ButterKnife.bind(this,this);

        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ImageShowActivity.class);
                intent.putExtra("imagesrc",mImageSrc);
                getContext().startActivity(intent);
            }
        });
    }

    public void setHeaderViewDetail(ZhihuDetailStroyBean bean){
        String title = bean.getTitle();
        mImageSrc = bean.getImage();
        String body = bean.getBody();

        mTextView.setText(title);

        int width = DeviceInfoUtil.getDeviceWidth(mContext);
        int height = width*3/4;
        Glide.with(mContext)
                .load(mImageSrc)
                .override(width,height)
                .placeholder(R.drawable.default_pic)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);

        mHtmlTextView.setHtmlFromString(body, new HtmlTextView.LocalImageGetter());

    }
}
