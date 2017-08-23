package com.example.fliest.freshnews.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.activity.TopNewsDetailActivity;
import com.example.fliest.freshnews.activity.ZhihuiDetailActivity;
import com.example.fliest.freshnews.adapter.RecyclerViewDecoration;
import com.example.fliest.freshnews.adapter.TopnewsViewAdapter;
import com.example.fliest.freshnews.adapter.ZhihuViewAdapter;
import com.example.fliest.freshnews.bean.topnews.TopnewsBean;
import com.example.fliest.freshnews.bean.zhihu.ZhihuList;
import com.example.fliest.freshnews.bean.zhihu.ZhihuStroyBean;
import com.example.fliest.freshnews.presenter.zhihu.ZhihuPresenter;
import com.example.fliest.freshnews.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fliest on 2017/7/12.
 */

public class ZhihuFragment extends BaseFragment<ZhihuList> {
    @BindView(R.id.xrecyclerview_zhihu)
    XRecyclerView recyclerView;
    @BindView(R.id.prograss_zhihu)
    ProgressBar mProgressBar;

    private ZhihuViewAdapter mViewAdapter;
    private ZhihuPresenter mPresenter;
    private String currentDate;

    @Override
    int getViewResId() {
        return R.layout.fragment_zhihu;
    }
    @Override
    void getBaseWidget() {
        baseRecyclerView = recyclerView;
    }

    public void initRecyclerView(){
        //baseRecyclerView = recyclerView;
        mLayoutManager = new LinearLayoutManager(getContext());
        mViewAdapter = new ZhihuViewAdapter(getContext());
        RecyclerViewDecoration viewDecoration = new RecyclerViewDecoration(getContext(), 1);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mViewAdapter);
        recyclerView.addItemDecoration(viewDecoration);

        recyclerView.setLoadingMoreEnabled(true);
    }

    public void initListener(){
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        recyclerView.refreshComplete();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        loadMoreData();
                        recyclerView.loadMoreComplete();
                    }
                }, 1500);
            }
        });

        mViewAdapter.setOnItemClickListener(new ZhihuViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, ZhihuStroyBean news, ImageView imageView) {
                int id = news.getId();
                Intent intent = new Intent(getContext(), ZhihuiDetailActivity.class);
                intent.putExtra("id", id);

                startActivity(intent);
            }
        });
    }

    public void initData() {
        mPresenter = new ZhihuPresenter(this);
    }

    @Override
    public void showProgressDialog() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        if (!mConnected) {
            Snackbar.make(recyclerView, "请检查网络。", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadData() {
        mPresenter.getLatestList();
    }

    @Override
    public void loadMoreData() {
        mPresenter.getLastList(currentDate);
    }

    @Override
    public void updateItems(ZhihuList list) {
        ArrayList<ZhihuStroyBean> stroyList = list.getStroyList();

        currentDate = list.getDate();

        mViewAdapter.addItems(stroyList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.unSubscription();
    }
}
