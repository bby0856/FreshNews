package com.example.fliest.freshnews.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.Utils.DeviceInfoUtil;
import com.example.fliest.freshnews.view.IView;
import com.example.fliest.freshnews.widget.MoveableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageShowActivity extends Activity implements IView{

    @BindView(R.id.moveablelayout)
    MoveableLayout moveableLayout;
    @BindView(R.id.imageview_show_activity)
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_show);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String imageSrc = intent.getStringExtra("imagesrc");

        int width = DeviceInfoUtil.getDeviceWidth(this);
        int height = width*3/4;
        Glide.with(this)
                .load(imageSrc)
                .override(width,height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.default_pic)
                .centerCrop()
                .into(mImageView);

        moveableLayout.setOnImageViewDismissListener(new MoveableLayout.OnImageViewDismissListener() {
            @Override
            public void onImageViewDismiss() {
                finish();
            }
        });
    }



    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void loadMoreData() {

    }

    @Override
    public void updateItems(Object o) {

    }
}
