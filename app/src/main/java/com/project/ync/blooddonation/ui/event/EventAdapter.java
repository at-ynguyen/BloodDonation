package com.project.ync.blooddonation.ui.event;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.api.response.EventResponse;
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
public class EventAdapter extends BaseAdapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOADING = 2;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnItemEventClickListener {
        void onItemClick(int position);
    }

    private List<EventResponse> mEventResponses;

    @Accessors(prefix = "m")
    @Setter
    private OnItemEventClickListener mListener;

    @Accessors(prefix = "m")
    @Setter
    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean mIsLoading;
    private int mVisibleThreshold = 1;
    private int mLastVisibleItem, mTotalItemCount;

    public EventAdapter(@NonNull Context context, List<EventResponse> eventResponses, RecyclerView recyclerView) {
        super(context);
        mEventResponses = eventResponses;
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
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
            return new EventViewHolder(view);
        } else {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EventViewHolder) {
            ((EventViewHolder) holder).mTvTime.setText(TimeUtil.parseDate(mEventResponses.get(position).getEvent().getCreateAt()));
            ((EventViewHolder) holder).mTvContent.setText(Html.fromHtml(mEventResponses.get(position).getEvent().getContent()));
            ((EventViewHolder) holder).mTvTitle.setText(mEventResponses.get(position).getEvent().getEventName());
            ((EventViewHolder) holder).mTvMember.setText(mEventResponses.get(position).getNumbers() + " người tham gia");
        } else if (holder instanceof LoadingViewHolder) {
            ((LoadingViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mEventResponses.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mEventResponses.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTime;
        TextView mTvContent;
        TextView mTvTitle;
        TextView mTvMember;
        RelativeLayout mRlItem;
        TextView mTvShare;

        public EventViewHolder(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvMember = (TextView) itemView.findViewById(R.id.tvMembers);
            mRlItem = (RelativeLayout) itemView.findViewById(R.id.rlItem);
            mRlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(getAdapterPosition());
                }
            });
            mTvShare = (TextView)itemView.findViewById(R.id.tvShare);
            mTvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = mEventResponses.get(getLayoutPosition()).getEvent().getId();
                    ShareUtils.SharingToSocialMedia("/index/view/event/" + id, getContext());
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
