package com.example.fliest.freshnews.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.bean.vedio.VedioBean;
import com.example.fliest.freshnews.view.IView;

public class VedioDetailActivity extends AppCompatActivity implements IView<VedioBean> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_detail);
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
    public void updateItems(VedioBean bean) {

    }
}
