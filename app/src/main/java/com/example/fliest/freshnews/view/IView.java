package com.example.fliest.freshnews.view;

/**
 * Created by Fliest on 2017/7/10.
 */

public interface IView<T> {

    void showProgressDialog();
    void hideProgressDialog();
    void showError(String error);

    void loadData();
    void loadMoreData();
    void updateItems(T t);
}
