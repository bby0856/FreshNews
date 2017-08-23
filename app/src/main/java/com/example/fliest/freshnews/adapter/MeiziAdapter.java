package com.example.fliest.freshnews.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.Utils.DeviceInfoUtil;
import com.example.fliest.freshnews.bean.gank.BeautyBean;
import com.example.fliest.freshnews.bean.zhihu.ZhihuStroyBean;
import com.example.fliest.freshnews.widget.FourThreeImageView;

import java.util.ArrayList;

/**
 * Created by Fliest on 2017/6/27.
 */

public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private OnItemClickListener mListener;
    private ArrayList<BeautyBean> mBeautyList;
    private Context mContext;

    public MeiziAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBeautyList = new ArrayList<BeautyBean>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_meizi, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BeautyBean beautyBean = mBeautyList.get(position);

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, position, beautyBean);
            }
        });

        int width = DeviceInfoUtil.getDeviceWidth(mContext);
        int height = width * 3 / 4;
        String imageUrl = beautyBean.getImageUrl();
        Glide.with(mContext)
                .load(imageUrl)
                //.override(width, height)
               // .placeholder(R.drawable.default_pic)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mBeautyList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        FourThreeImageView mImageView;
        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout_item_meizi);
            mImageView = (FourThreeImageView) itemView.findViewById(R.id.imageview_meizi);
            mCardView = (CardView) itemView.findViewById(R.id.cardview_meizi);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, BeautyBean beauty);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void addItems(ArrayList<BeautyBean> list) {
        mBeautyList.addAll(list);
        notifyDataSetChanged();
    }

    public void removeItem() {

    }
}
