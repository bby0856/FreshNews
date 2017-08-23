package com.example.fliest.freshnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.bean.topnews.TopnewsBean;
import com.example.fliest.freshnews.bean.vedio.VedioBean;
import com.example.fliest.freshnews.config.Config;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Fliest on 2017/6/27.
 */

public class VedioViewAdapter extends RecyclerView.Adapter<VedioViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private OnItemClickListener mListener;
    private ArrayList<VedioBean> mVedioList;
    private Context mContext;

    public VedioViewAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mVedioList = new ArrayList<VedioBean>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_vedio,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        VedioBean vedioBean = mVedioList.get(position);
        final String title = vedioBean.getDesc();
        //final String urlString = vedioBean.getUrl();
        final String urlString = Config.getVedioUrlRandom();

        holder.mTitleTv.setText(title);



        holder.mVideoPlayer.setUp(urlString, JCVideoPlayer.SCREEN_LAYOUT_LIST,title);
        holder.mVideoPlayer.titleTextView.setText("");//清除标题,防止复用的时候出现


        Picasso.with(holder.mVideoPlayer.getContext())
                .load(urlString)
                .into(holder.mVideoPlayer.thumbImageView);
        /*Glide.with(holder.mVideoPlayer.getContext())
                .load(urlString)
               // .placeholder(R.drawable.default_pic)
                .into(holder.mVideoPlayer.thumbImageView);*/
    }

    @Override
    public int getItemCount() {
        return mVedioList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        private final JCVideoPlayerStandard mVideoPlayer;
        private final TextView mTitleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mVideoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.video_player);
            mTitleTv = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position, TopnewsBean news);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void addItems(ArrayList<VedioBean> list){

        mVedioList.addAll(list);


        notifyDataSetChanged();
    }

    public void removeItem(){
        mVedioList.remove(mVedioList.size()-1);
        notifyDataSetChanged();
    }
}
