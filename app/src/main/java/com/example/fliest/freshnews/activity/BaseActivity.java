package com.example.fliest.freshnews.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.other.SwipeBackHelper;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.ButterKnife;

/**
 * Created by Fliest on 2017/8/6.
 */

public abstract class BaseActivity extends AppCompatActivity {

    Toolbar baseToolbar;
    XRecyclerView baseXRecyclerView;
    SwipeBackHelper mSwipeBackHelper;
    ImageView baseLeftBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getViewResId());

        ButterKnife.bind(this);
        getBaseWidget();
        init();

        initView();
        initData();
        initListener();
    }


    abstract int getViewResId();
    abstract void getBaseWidget();
    public void init(){}

    public void initView() {
        mSwipeBackHelper = new SwipeBackHelper();
        mSwipeBackHelper.onCreate(this);

        initToolbar();

        initRecyclerView();

        setDecorViewUI();
    }
    public void initData(){}
    public void initListener() {
        /*baseToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        baseLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeBackHelper.getCurrentPage(BaseActivity.this).getSlidingLayout().onScrollClose();
                finish();
            }
        });
        baseToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseXRecyclerView.scrollTo(0, 0);
            }
        });
    }


    public void initToolbar(){
        setSupportActionBar(baseToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //baseToolbar.setNavigationIcon(R.drawable.back);
    }
    public void initRecyclerView() {
    }
    public void setDecorViewUI() {
        View decorView = getWindow().getDecorView();
        int uiOption = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOption);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onBackPressed() {
        mSwipeBackHelper.getCurrentPage(this).getSlidingLayout().onScrollClose();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SwipeBackHelper.onDestroy(this);
    }

}
