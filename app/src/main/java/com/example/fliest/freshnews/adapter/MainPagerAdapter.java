package com.example.fliest.freshnews.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.fliest.freshnews.fragment.ContentFragment;
import com.example.fliest.freshnews.fragment.MeFragment;
import com.example.fliest.freshnews.fragment.MeiziFragment;
import com.example.fliest.freshnews.fragment.TopNewsFragment;
import com.example.fliest.freshnews.fragment.VedioFragment;
import com.example.fliest.freshnews.fragment.ZhihuFragment;

import java.util.ArrayList;

/**
 * Created by Fliest on 2017/7/20.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> mFragments ;
    public MainPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);

        mFragments = new ArrayList<Fragment>();
        mFragments.add(new ContentFragment());
        mFragments.add(new MeFragment());
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
