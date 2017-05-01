package com.project.ync.blooddonation.ui.award;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.model.Award;
import com.project.ync.blooddonation.ui.BaseAdapter;

import java.util.List;

/**
 * @author YNC
 */
public class AwardAdapter extends BaseAdapter<RecyclerView.ViewHolder> {
    private List<Award> mAward;

    public AwardAdapter(@NonNull Context context, List<Award> awards) {
        super(context);
        mAward = awards;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_award, parent, false);
        return new AwardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AwardViewHolder) {
            ((AwardViewHolder) holder).tvName.setText(mAward.get(position).getUser().getFullName());
            ((AwardViewHolder) holder).tvBloodType.setText("Nhóm máu: " + mAward.get(position).getUser().getBloodType());
            ((AwardViewHolder) holder).tvNumber.setText("Số lần hiến: " + mAward.get(position).getNumber());
        }
    }

    @Override
    public int getItemCount() {
        return mAward.size();
    }

    class AwardViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvBloodType;
        TextView tvNumber;

        public AwardViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvBloodType = (TextView) itemView.findViewById(R.id.tvBloodType);
            tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
        }
    }
}
