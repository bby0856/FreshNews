package com.example.fliest.freshnews.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.activity.MeiziDetailActivity;
import com.example.fliest.freshnews.activity.TopNewsDetailActivity;
import com.example.fliest.freshnews.adapter.MeiziAdapter;
import com.example.fliest.freshnews.adapter.RecyclerViewDecoration;
import com.example.fliest.freshnews.adapter.TopnewsViewAdapter;
import com.example.fliest.freshnews.bean.gank.BeautyBean;
import com.example.fliest.freshnews.bean.gank.BeautyList;
import com.example.fliest.freshnews.bean.topnews.TopnewsBean;
import com.example.fliest.freshnews.bean.topnews.TopnewsList;
import com.example.fliest.freshnews.presenter.gank.GankPresenter;
import com.example.fliest.freshnews.presenter.topnews.TopNewsPresenter;
import com.example.fliest.freshnews.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Fliest on 2017/7/7.
 */

public class MeiziFragment extends BaseFragment<BeautyList> {
    @BindView(R.id.xrecyclerview_meizi)
    XRecyclerView recyclerView;
    @BindView(R.id.prograss_meizi)
    ProgressBar mProgressBar;

    private int currentIndex;
    private MeiziAdapter mViewAdapter;
    private GankPresenter mGankPresenter;

    @Override
    int getViewResId() {
        return R.layout.fragment_meizi;
    }
    @Override
    void getBaseWidget() {
        baseRecyclerView = recyclerView;
    }

    public void initRecyclerView(){
        //baseRecyclerView = recyclerView;
        mLayoutManager = new LinearLayoutManager(getContext());
        mViewAdapter = new MeiziAdapter(getContext());
        //RecyclerViewDecoration viewDecoration = new RecyclerViewDecoration(getContext(), 1);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mViewAdapter);
        //recyclerView.addItemDecoration(viewDecoration);

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

        mViewAdapter.setOnItemClickListener(new MeiziAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BeautyBean bean) {
                Intent intent = new Intent(getContext(), MeiziDetailActivity.class);
                intent.putExtra("url", bean.getImageUrl());
                startActivity(intent);
            }
        });
    }

    public void initData() {
        mGankPresenter = new GankPresenter(this);
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
        currentIndex = 1;
        mGankPresenter.getMeiziList(currentIndex);
    }

    @Override
    public void loadMoreData() {
        currentIndex += 1;
        mGankPresenter.getMeiziList(currentIndex);
    }


    @Override
    public void updateItems(BeautyList list) {
        mViewAdapter.addItems(list.getResults());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mGankPresenter.unSubscription();
    }


}
