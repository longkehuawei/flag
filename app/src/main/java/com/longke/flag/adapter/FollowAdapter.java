package com.longke.flag.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longke.flag.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by longke on 2017/12/16.
 */

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_weiboitem_original_pictext, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.titlebar_layout)
        LinearLayout mTitlebarLayout;
        @InjectView(R.id.progressBar)
        ProgressBar mProgressBar;
        @InjectView(R.id.item_layout)
        LinearLayout mItemLayout;
        @InjectView(R.id.redirect)
        TextView mRedirect;
        @InjectView(R.id.bottombar_retweet)
        LinearLayout mBottombarRetweet;
        @InjectView(R.id.comment)
        TextView mComment;
        @InjectView(R.id.bottombar_comment)
        LinearLayout mBottombarComment;
        @InjectView(R.id.feedlike)
        TextView mFeedlike;
        @InjectView(R.id.bottombar_attitude)
        LinearLayout mBottombarAttitude;
        @InjectView(R.id.bottombar_layout)
        LinearLayout mBottombarLayout;
        @InjectView(R.id.origin_weibo_layout)
        LinearLayout mOriginWeiboLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);


        }
    }

    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
