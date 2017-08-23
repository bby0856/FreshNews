package com.example.fliest.freshnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.bean.topnews.TopnewsDetailBean;

/**
 * Created by Fliest on 2017/8/3.
 */

public class ZhihuDetailRecyclerAdapter extends RecyclerView.Adapter<ZhihuDetailRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private final LayoutInflater mInflater;
    private TopnewsDetailBean mBean;

    public ZhihuDetailRecyclerAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_zhihui_detail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{



        public ViewHolder(View itemView) {
            super(itemView);


        }
    }

    private void updateItem(TopnewsDetailBean bean){

    }
}
