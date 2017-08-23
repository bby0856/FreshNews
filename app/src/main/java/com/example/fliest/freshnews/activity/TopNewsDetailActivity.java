package com.example.fliest.freshnews.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.adapter.TopNewsDetailRecyclerAdapter;
import com.example.fliest.freshnews.bean.topnews.TopnewsDetailBean;
import com.example.fliest.freshnews.other.SwipeBackHelper;
import com.example.fliest.freshnews.presenter.topnews.TopNewsDetailPresenter;
import com.example.fliest.freshnews.ui.TopNewsDetailHeaderView;
import com.example.fliest.freshnews.view.IView;
import com.example.fliest.freshnews.widget.SlidingLayout;
import com.example.fliest.freshnews.widget.SwipeBackLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


import butterknife.BindView;
import butterknife.ButterKnife;


public class TopNewsDetailActivity extends BaseActivity implements IView<TopnewsDetailBean> {

    @BindView(R.id.toolbar_detail)
    Toolbar toolbar;
    @BindView(R.id.image_left_back_tnd)
    ImageView mImageView;
    @BindView(R.id.recycler_view_top_news_detail)
    XRecyclerView mRecyclerView;

    private TopNewsDetailPresenter mPresneter;
    private String mDocid;
    private String mImageSrc;
    private TopNewsDetailHeaderView mTopNewsDetailHeaderView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    int getViewResId() {
        return R.layout.activity_top_news_detail2;
    }

    @Override
    void getBaseWidget() {
        baseToolbar = toolbar;
        baseXRecyclerView = mRecyclerView;
        baseLeftBack = mImageView;
    }

    @Override
    public void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        TopNewsDetailRecyclerAdapter topNewsDetailRecyclerAdapter = new TopNewsDetailRecyclerAdapter(this);
        //RecyclerViewDecoration viewDecoration = new RecyclerViewDecoration(getContext(), 1);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(topNewsDetailRecyclerAdapter);

        mRecyclerView.setPullRefreshEnabled(false);

        mTopNewsDetailHeaderView = new TopNewsDetailHeaderView(this);
        mRecyclerView.addHeaderView(mTopNewsDetailHeaderView);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mDocid = intent.getStringExtra("docid");
        mImageSrc = intent.getStringExtra("image");

        mPresneter = new TopNewsDetailPresenter(this);

        loadData();
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
        mPresneter.getNewsDetail(mDocid);
    }

    @Override
    public void loadMoreData() {

    }


    @Override
    public void updateItems(TopnewsDetailBean bean) {
        if (bean.getImg().size() == 0) {
            bean.getImg().get(0).setImageSrc(mImageSrc);
        }

        mTopNewsDetailHeaderView.setHeaderViewDetail(bean);
    }

}
