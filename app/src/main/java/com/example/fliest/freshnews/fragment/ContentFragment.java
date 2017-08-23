package com.example.fliest.freshnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.activity.ChannelActivity;
import com.example.fliest.freshnews.adapter.ContentPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fliest on 2017/7/20.
 */

public class ContentFragment extends Fragment {

    @BindView(R.id.viewpage_indicator)
    TabPageIndicator mTabPageIndicator;
    @BindView(R.id.viewpager_content_fragment)
    ViewPager mViewPager;
    @BindView(R.id.image_add_channel_cf)
    ImageView imageView;
    private ContentPagerAdapter mContentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContentPagerAdapter = new ContentPagerAdapter(getChildFragmentManager(), getActivity());
        mViewPager.setAdapter(mContentPagerAdapter);

        mTabPageIndicator.setViewPager(mViewPager);

        mTabPageIndicator.setOnTabReselectedListener(new TabPageIndicator.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {

                if (position <= 3) {
                    BaseFragment baseFragment = mContentPagerAdapter.getItem(position);
                    LinearLayoutManager manager = (LinearLayoutManager) baseFragment.baseRecyclerView.getLayoutManager();
                    //baseFragment.mLayoutManager.scrollToPositionWithOffset(0, 0);
                    manager.scrollToPositionWithOffset(0, 0);

                    baseFragment.baseRecyclerView.refresh();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), ChannelActivity.class),1);

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("ContentFragment onActivityResult");

        //mContentPagerAdapter.updateFragment();


        mViewPager.setAdapter(null);
        mViewPager.setAdapter(new ContentPagerAdapter(getChildFragmentManager(), getActivity()));
        mTabPageIndicator.notifyDataSetChanged();

        System.out.println("count:"+mViewPager.getAdapter().getCount());


    }
}
