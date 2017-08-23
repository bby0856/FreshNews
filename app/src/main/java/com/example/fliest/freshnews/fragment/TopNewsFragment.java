package com.example.fliest.freshnews.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.activity.TopNewsDetailActivity;
import com.example.fliest.freshnews.adapter.TopnewsViewAdapter;
import com.example.fliest.freshnews.adapter.RecyclerViewDecoration;
import com.example.fliest.freshnews.bean.topnews.TopnewsBean;
import com.example.fliest.freshnews.bean.topnews.TopnewsList;
import com.example.fliest.freshnews.presenter.topnews.TopNewsPresenter;
import com.jcodecraeer.xrecyclerview.ArrowRefreshHeader;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Fliest on 2017/7/7.
 */

public class TopNewsFragment extends BaseFragment<TopnewsList> {
    @BindView(R.id.xrecyclerview_top)
    XRecyclerView recyclerView;
    @BindView(R.id.prograss_topnews)
    ProgressBar mProgressBar;

    private int currentIndex;
    private TopnewsViewAdapter mViewAdapter;
    private TopNewsPresenter mTopNewsPresenter;


   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        ButterKnife.bind(this, view);
        isViewCreate = true;
        return view;
    }*/

    @Override
    int getViewResId() {
        return R.layout.fragment_top;
    }

    @Override
    void getBaseWidget() {
        baseRecyclerView = recyclerView;
    }

    public void initRecyclerView(){
        //

        mLayoutManager = new LinearLayoutManager(getContext());
        mViewAdapter = new TopnewsViewAdapter(getContext());
        RecyclerViewDecoration viewDecoration = new RecyclerViewDecoration(getContext(), 1);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mViewAdapter);
        recyclerView.addItemDecoration(viewDecoration);

        recyclerView.setLoadingMoreEnabled(true);
    }

    public void initListener() {
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

        mViewAdapter.setOnItemClickListener(new TopnewsViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, TopnewsBean news) {
                Intent intent = new Intent(getActivity(), TopNewsDetailActivity.class);
                intent.putExtra("docid", news.getDocid());
                intent.putExtra("image", news.getImgsrc());
                startActivity(intent);
            }
        });
    }

    public void initData() {
        mTopNewsPresenter = new TopNewsPresenter(this);
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
        if (error.contains("403")) {
            if (recyclerView != null) {
                Snackbar.make(recyclerView, "没有更多内容了，请刷新。", Snackbar.LENGTH_SHORT)
                        .setAction("刷新", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recyclerView.refresh();
                            }
                        })
                        .show();
            }
        }
        if (!mConnected) {
            if(recyclerView!=null){
                Snackbar.make(recyclerView, "请检查网络。", Snackbar.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void loadData() {
        currentIndex = 0;
        mTopNewsPresenter.getNewsList(currentIndex);
    }

    @Override
    public void loadMoreData() {
        currentIndex += 20;
        mTopNewsPresenter.getNewsList(currentIndex);
    }


    @Override
    public void updateItems(TopnewsList list) {
        mViewAdapter.addItems(list.getTopnewsList());
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        mTopNewsPresenter.unSubscription();
    }
}
