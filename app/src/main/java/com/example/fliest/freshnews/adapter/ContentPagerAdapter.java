package com.example.fliest.freshnews.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.fliest.freshnews.config.Config;
import com.example.fliest.freshnews.fragment.BaseFragment;
import com.example.fliest.freshnews.fragment.EmptyFragment;
import com.example.fliest.freshnews.fragment.MeiziFragment;
import com.example.fliest.freshnews.fragment.TopNewsFragment;
import com.example.fliest.freshnews.fragment.VedioFragment;
import com.example.fliest.freshnews.fragment.ZhihuFragment;

import java.util.ArrayList;

/**
 * Created by Fliest on 2017/7/7.
 */

public class ContentPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<BaseFragment> mFragments;
    private Context mContext;
    private String[] channel;

    public ContentPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);

        System.out.println("ContentPagerAdapter");
        mContext = activity;

        mFragments = new ArrayList<BaseFragment>();
        mFragments.add(new TopNewsFragment());
        mFragments.add(new ZhihuFragment());
        mFragments.add(new MeiziFragment());
        mFragments.add(new VedioFragment());

        updateFragment();
    }

    @Override
    public BaseFragment getItem(int position) {

        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return channel[position];
    }

    /*@Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }*/

    public void updateFragment(){
        channel = Config.getChannel(mContext);
        int emptyCount = channel.length - 4;

        if (emptyCount > 0) {
            for (int i = 0; i < emptyCount; i++) {
                Bundle bundle = new Bundle();
                bundle.putString("title",channel[i+4]);
                EmptyFragment emptyFragment = new EmptyFragment();
                emptyFragment.setArguments(bundle);
                mFragments.add(emptyFragment);
            }
        }
    }
}
