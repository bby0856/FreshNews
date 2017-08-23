package com.example.fliest.freshnews.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.ButterKnife;

/**
 * Created by Fliest on 2017/7/14.
 */

public abstract class BaseFragment<T> extends Fragment implements IView<T>{
    public boolean mConnected = false;
    public ConnectivityManager.NetworkCallback mNetworkCallback;
    public boolean isViewCreate = false;
    public boolean isVisible;

    public XRecyclerView baseRecyclerView;
    public LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getViewResId(), container, false);
        ButterKnife.bind(this, view);
        isViewCreate = true;
        return view;
    }

    abstract int getViewResId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBaseWidget();

        initView();
        initData();
        initNetWorkListener();

        mConnected = checkConnectity();

        lazyLoadData();
    }

    abstract void getBaseWidget();

    public void initView(){
        initRecyclerView();

        initListener();
    }
    public void initData(){}

    public void initRecyclerView(){}
    public void initListener(){}

    public void initNetWorkListener() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            builder = new NetworkRequest.Builder();
            mNetworkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mConnected == false){
                                hideProgressDialog();
                                loadData();
                            }
                            mConnected = true;
                        }
                    });
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);

                    mConnected = false;
                }
            };
            cm.registerNetworkCallback(builder.build(), mNetworkCallback);
        }
    }

    public boolean checkConnectity() {
        boolean isNetAvailable = false;
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            isNetAvailable = false;
        } else if (networkInfo.isAvailable()) {
            isNetAvailable = true;
        }

        return isNetAvailable;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            lazyLoadData();
        } else {
            isVisible = false;
        }
    }

    protected void lazyLoadData(){
        if(isViewCreate&&isVisible){
            if (mConnected) {
                loadData();
            } else {
                showProgressDialog();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cm.unregisterNetworkCallback(mNetworkCallback);
        }
    }
}
