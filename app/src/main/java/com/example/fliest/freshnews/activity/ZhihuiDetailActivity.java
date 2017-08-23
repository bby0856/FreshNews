package com.example.fliest.freshnews.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.Utils.DeviceInfoUtil;
import com.example.fliest.freshnews.adapter.ZhihuDetailRecyclerAdapter;
import com.example.fliest.freshnews.bean.zhihu.ZhihuDetailStroyBean;
import com.example.fliest.freshnews.other.SwipeBackHelper;
import com.example.fliest.freshnews.presenter.zhihu.ZhihuDetailPresenter;
import com.example.fliest.freshnews.ui.ZhihuDetailHeaderView;
import com.example.fliest.freshnews.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhihuiDetailActivity extends BaseActivity implements IView<ZhihuDetailStroyBean>{

    @BindView(R.id.toolbar_zhihu_detail)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_zhihu_detail)
    XRecyclerView mRecyclerView;
    @BindView(R.id.image_left_back_zhd)
    ImageView mImageView;

    private ZhihuDetailPresenter mPresenter;
    private ZhihuDetailHeaderView mZhihuDetailHeaderView;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    int getViewResId() {
        return R.layout.activity_zhihui_detail2;
    }

    @Override
    void getBaseWidget() {
        baseToolbar = toolbar;
        baseXRecyclerView = mRecyclerView;
        baseLeftBack = mImageView;
    }


    public void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ZhihuDetailRecyclerAdapter topNewsDetailRecyclerAdapter = new ZhihuDetailRecyclerAdapter(this);
        //RecyclerViewDecoration viewDecoration = new RecyclerViewDecoration(getContext(), 1);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(topNewsDetailRecyclerAdapter);

        mRecyclerView.setPullRefreshEnabled(false);
        mZhihuDetailHeaderView = new ZhihuDetailHeaderView(this);
        mRecyclerView.addHeaderView(mZhihuDetailHeaderView);
    }

    public void initData(){
        Intent intent = getIntent();
        mId = intent.getIntExtra("id",0);

        mPresenter = new ZhihuDetailPresenter(this);
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
        mPresenter.getStoryDetail(mId);
    }

    @Override
    public void loadMoreData() {

    }

    @Override
    public void updateItems(ZhihuDetailStroyBean bean) {
        mZhihuDetailHeaderView.setHeaderViewDetail(bean);
    }
}
