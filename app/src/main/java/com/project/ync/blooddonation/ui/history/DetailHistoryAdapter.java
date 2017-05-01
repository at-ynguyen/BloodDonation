package com.project.ync.blooddonation.ui.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.model.History;
import com.project.ync.blooddonation.ui.BaseAdapter;
import com.project.ync.blooddonation.util.TimeUtil;

import java.util.List;

/**
 * @author YNC
 */
public class DetailHistoryAdapter extends BaseAdapter<RecyclerView.ViewHolder> {
    private List<History> mHistorys;

    public DetailHistoryAdapter(@NonNull Context context, List<History> histories) {
        super(context);
        mHistorys = histories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_detail_history, parent, false);
        return new DetaulHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetaulHistoryViewHolder) {
            ((DetaulHistoryViewHolder) holder).mTvNote.setText(mHistorys.get(position).getNote());
            ((DetaulHistoryViewHolder) holder).mTvTime.setText(TimeUtil.convertTime(mHistorys.get(position).getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return mHistorys.size();
    }

    class DetaulHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTime;
        TextView mTvNote;

        public DetaulHistoryViewHolder(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvNote = (TextView) itemView.findViewById(R.id.tvNote);
        }
    }
}
