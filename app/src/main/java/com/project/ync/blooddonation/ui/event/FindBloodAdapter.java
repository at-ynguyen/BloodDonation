package com.project.ync.blooddonation.ui.event;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.project.ync.blooddonation.util.TimeUtil;
import com.project.ync.blooddonation.widget.LoadingBar;

import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author YNC
 */
public class FindBloodAdapter extends BaseAdapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOADING = 2;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnFindBloodClickListener {
        void onClick(int position);
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

    public FindBloodAdapter(@NonNull Context context, List<FindBlood> findBloods, RecyclerView recyclerView) {
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
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_find_blood, parent, false);
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

        public FindBloodViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mImgView = (ImageView) itemView.findViewById(R.id.imgView);
            mLlFind = (LinearLayout) itemView.findViewById(R.id.llFind);
            mTvShare = (TextView) itemView.findViewById(R.id.tvShare);
            mTvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String urlToShare = getString(R.string.url_host) + mFindBloods.get(getAdapterPosition()).getImage();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
                    boolean facebookAppFound = false;
                    List<ResolveInfo> matches = getContext().getPackageManager().queryIntentActivities(intent, 0);
                    for (ResolveInfo info : matches) {
                        if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                            intent.setPackage(info.activityInfo.packageName);
                            facebookAppFound = true;
                            break;
                        }
                    }

                    if (!facebookAppFound) {
                        String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                    }

                    getContext().startActivity(intent);
                }
            });
            mLlFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(getAdapterPosition());
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
