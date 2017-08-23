package com.example.fliest.freshnews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;

import com.example.fliest.freshnews.adapter.ContentPagerAdapter;
import com.example.fliest.freshnews.adapter.MainPagerAdapter;
import com.example.fliest.freshnews.other.SwipeBackHelper;
import com.example.fliest.freshnews.widget.NoScrollViewPager;
import com.example.fliest.freshnews.widget.TabButtonGroup;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.viewpager_main)
    NoScrollViewPager mViewPager;
    @BindView(R.id.tab_button_group)
    TabButtonGroup mButtonGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        /*Bmob.initialize(this, "056457ecbfe3e210a2b52b25950b6417");
        BmobInstallation.getCurrentInstallation().save();
        BmobPush.startWork(this);*/

        ButterKnife.bind(this);

        SwipeBackHelper swipeBackHelper = new SwipeBackHelper();
        swipeBackHelper.onCreate(this);
        swipeBackHelper.getCurrentPage(this)
                .setRequestDisallowInterceptTouchEvent(false)
                .setSwipeBackEnable(false);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mainPagerAdapter);


        View decorView = getWindow().getDecorView();
        int uiOption = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOption);
        decorView.setBackgroundColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mButtonGroup.setOnCheckChangeListener(new TabButtonGroup.OnCheckChangeListener() {
            @Override
            public void onCheckChange(int id) {
                switch (id) {
                    case R.id.news_bottom_tab_btn:
                        mViewPager.setCurrentItem(0, true);
                        break;
                    case R.id.zhihu_bottom_tab_btn:
                        mViewPager.setCurrentItem(1, true);
                        break;
                    /*case R.id.beauty_bottom_tab_btn:
                        mViewPager.setCurrentItem(2,true);
                        break;
                    case R.id.vedio_bottom_tab_btn:
                        mViewPager.setCurrentItem(3,true);
                        break;
                    case R.id.me_bottom_tab_btn:
                        mViewPager.setCurrentItem(4,true);
                        break;*/
                }


            }
        });
    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SwipeBackHelper.onDestroy(this);
    }

}
