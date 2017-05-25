package com.project.ync.blooddonation.ui.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.model.FindBlood;
import com.project.ync.blooddonation.ui.BaseAdapter;
import com.project.ync.blooddonation.util.ShareUtils;
import com.project.ync.blooddonation.util.TimeUtil;
import com.project.ync.blooddonation.widget.LoadingBar;

import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author YNC
 */
public class ListFindBloodAdapter extends BaseAdapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOADING = 2;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnFindBloodClickListener {
        void onClick(int position);

        void onCheck(int position);

        void onEdit(int position);
    }

    private List<FindBlood> mFindBloods;
    @Accessors(prefix = "m")
    @Setter
    private OnFindBloodClickListener mListener;
    @Accessors(prefix = "m")
    @Setter
    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean mIsLoading;
    private int mVisibleThreshold = 1;
    private int mLastVisibleItem, mTotalItemCount;

    public ListFindBloodAdapter(@NonNull Context context, List<FindBlood> findBloods, RecyclerView recyclerView) {
        super(context);
        mFindBloods = findBloods;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mTotalItemCount = linearLayoutManager.getItemCount();
                mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    mIsLoading = true;
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_list_find_blood, parent, false);
            return new FindBloodViewHolder(view);
        } else {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FindBloodViewHolder) {
            ((FindBloodViewHolder) holder).mTvTime.setText(TimeUtil.parseDate(mFindBloods.get(position).getCreateAt()));
            ((FindBloodViewHolder) holder).mTvContent.setText(mFindBloods.get(position).getPostContent());
            ((FindBloodViewHolder) holder).mTvName.setText(mFindBloods.get(position).getUser().getFullName());
            ((FindBloodViewHolder) holder).mTvTitle.setText(mFindBloods.get(position).getPostName());
            ((FindBloodViewHolder) holder).mImgCheck.setSelected(mFindBloods.get(position).isDone());
            if (mFindBloods.get(position).getImage().equals("") || mFindBloods.get(position).getImage() == null) {
                ((FindBloodViewHolder) holder).mImgView.setVisibility(View.GONE);
            } else {
                ((FindBloodViewHolder) holder).mImgView.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(getString(R.string.url_host) + mFindBloods.get(position).getImage()).centerCrop().into(((FindBloodViewHolder) holder).mImgView);
            }
        } else if (holder instanceof LoadingViewHolder) {
            ((LoadingViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mFindBloods != null ? mFindBloods.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mFindBloods.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    class FindBloodViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTime;
        TextView mTvContent;
        TextView mTvName;
        TextView mTvTitle;
        ImageView mImgView;
        LinearLayout mLlFind;
        TextView mTvShare;
        ImageView mImgEdit;
        ImageView mImgCheck;

        public FindBloodViewHolder(View itemView) {
            super(itemView);
            Log.i("TAG111", "AAAAAAAAAAAAAAAA");
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mImgView = (ImageView) itemView.findViewById(R.id.imgView);
            mLlFind = (LinearLayout) itemView.findViewById(R.id.llFind);
            mTvShare = (TextView) itemView.findViewById(R.id.tvShare);
            mImgCheck = (ImageView) itemView.findViewById(R.id.imgCheck);
            mImgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            mTvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = mFindBloods.get(getAdapterPosition()).getId();
                    ShareUtils.SharingToSocialMedia("index/view/findblood" + id, getContext());
                }
            });
            mLlFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(getAdapterPosition());
                }
            });
            mImgCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mFindBloods.get(getAdapterPosition()).isDone()) {
                        mImgCheck.setSelected(true);
                        mListener.onCheck(getAdapterPosition());
                        mListener.onEdit(getAdapterPosition());
                    }
                }
            });

        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (LoadingBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public void setLoaded() {
        mIsLoading = false;
    }
}
