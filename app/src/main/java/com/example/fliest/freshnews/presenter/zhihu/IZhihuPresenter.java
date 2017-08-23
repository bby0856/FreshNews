package com.example.fliest.freshnews.presenter.zhihu;

/**
 * Created by Fliest on 2017/7/10.
 */

public interface IZhihuPresenter {

    void getLatestList();
    void getLastList(String date);
    void getStoryDetail(int id);
}
