package com.example.fliest.freshnews.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.Utils.GlideCacheUtil;

import java.io.File;
import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Fliest on 2017/7/14.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.circle_imageview)
    CircleImageView mImageView;
    @BindView(R.id.text_cache_data)
    TextView mTextView;
    @BindView(R.id.ll_login)
    LinearLayout loginLL;
    @BindView(R.id.linear_clear_cache)
    LinearLayout mLinearLayout;

    private final GlideCacheUtil glideCacheUtil = GlideCacheUtil.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    int getViewResId() {
        return 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        loginLL.setOnClickListener(this);
        mLinearLayout.setOnClickListener(this);
    }

    @Override
    void getBaseWidget() {

    }

    private void showDialog() {

        AlertDialog.Builder clearCacheDialog = new AlertDialog.Builder(getContext());
        clearCacheDialog.setTitle("提示");
        clearCacheDialog.setMessage("确定清除缓存吗？");
        clearCacheDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearCache();
                dialog.dismiss();
            }
        });
        clearCacheDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        clearCacheDialog.show();
    }

    private void clearCache(){
        if (Looper.myLooper() == Looper.getMainLooper()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.get(getContext()).clearDiskCache();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(glideCacheUtil.getInternalCacheSize(getContext()));
                        }
                    });
                }
            }).start();
        } else {
            Glide.get(getContext()).clearDiskCache();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(glideCacheUtil.getInternalCacheSize(getContext()));
                }
            });
        }
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();


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
        mTextView.setText(glideCacheUtil.getInternalCacheSize(getContext()));
    }

    @Override
    public void loadMoreData() {

    }

    @Override
    public void updateItems(Object o) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_login:
                showDialogFragment();
                break;
            case R.id.linear_clear_cache:
                showDialog();
                break;
        }
    }

    private void showDialogFragment(){
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.show(getFragmentManager(), "createDialogFragment");
    }
}
