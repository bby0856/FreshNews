package com.example.fliest.freshnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fliest.freshnews.R;

import butterknife.ButterKnife;

/**
 * Created by Fliest on 2017/8/15.
 */

public class EmptyFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty,container,false);

        TextView textView = (TextView) view.findViewById(R.id.tv_show_ef);
        String title = getArguments().getString("title");
        textView.setText(title);

        return view;
    }

    @Override
    int getViewResId() {
        return 0;
    }

    @Override
    void getBaseWidget() {

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
    public void updateItems(Object o) {

    }
}
