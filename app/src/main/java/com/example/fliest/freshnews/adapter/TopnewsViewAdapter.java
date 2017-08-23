package com.example.fliest.freshnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.bean.topnews.TopnewsBean;

import java.util.ArrayList;

/**
 * Created by Fliest on 2017/6/27.
 */

public class TopnewsViewAdapter extends RecyclerView.Adapter<TopnewsViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private OnItemClickListener mListener;
    private ArrayList<TopnewsBean> mNewsList;
    private Context mContext;

    public TopnewsViewAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mNewsList = new ArrayList<TopnewsBean>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_top,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TopnewsBean news = mNewsList.get(position);
        String title = news.getTitle();
        String imageSrc = news.getImgsrc();

        Glide.with(mContext)
                .load(imageSrc)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.default_pic)
                .centerCrop()
                .into(holder.mImageView);
        holder.mTextView.setText(title);

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view,position,mNewsList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private LinearLayout mLinearLayout;
        private TextView mTextView;



        public ViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout_item_top);
            mImageView = (ImageView) itemView.findViewById(R.id.image_item_top);
            mTextView = (TextView) itemView.findViewById(R.id.text_item_top);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position,TopnewsBean news);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void addItems(ArrayList<TopnewsBean> list){
        list.remove(0);
        mNewsList.addAll(list);

        notifyDataSetChanged();
    }

    public void removeItem(){
        mNewsList.remove(mNewsList.size()-1);
        notifyDataSetChanged();
    }
}
