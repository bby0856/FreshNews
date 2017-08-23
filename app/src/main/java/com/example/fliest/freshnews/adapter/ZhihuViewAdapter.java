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
import com.example.fliest.freshnews.bean.zhihu.ZhihuStroyBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fliest on 2017/6/27.
 */

public class ZhihuViewAdapter extends RecyclerView.Adapter<ZhihuViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private OnItemClickListener mListener;
    private ArrayList<ZhihuStroyBean> mStroyList;
    private Context mContext;

    public ZhihuViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mStroyList = new ArrayList<ZhihuStroyBean>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_zhihu, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ZhihuStroyBean stroyBean = mStroyList.get(position);

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, position, stroyBean,holder.mImageView);
            }
        });

        String[] images = stroyBean.getImages();
        Glide.with(mContext)
                .load(images[0])
                .centerCrop()
                .placeholder(R.drawable.default_pic)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.mImageView);

        holder.mTextView.setText(stroyBean.getTitle());

    }

    @Override
    public int getItemCount() {
        return mStroyList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        ImageView mImageView;
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout_item_zhihu);
            mImageView = (ImageView) itemView.findViewById(R.id.image_item_zhihu);
            mTextView = (TextView) itemView.findViewById(R.id.text_item_zhihu);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ZhihuStroyBean news,ImageView imageView);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void addItems(ArrayList<ZhihuStroyBean> list) {
        mStroyList.addAll(list);
        notifyDataSetChanged();
    }

    public void removeItem() {

    }
}
