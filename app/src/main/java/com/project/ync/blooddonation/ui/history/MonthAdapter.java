package com.project.ync.blooddonation.ui.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.ui.BaseAdapter;
import com.project.ync.blooddonation.util.TimeUtil;

import java.util.Calendar;

/**
 * @author Ync.
 */
public class MonthAdapter extends BaseAdapter<MonthAdapter.ViewHolder> {
    /**
     * OnItemClickListener
     */
    public interface OnItemClickListener {
        void onDayClick(Calendar date);
    }

    private static final int NUMBER_DAY_OF_WEEK = 7;

    private Calendar mCalendar;
    private int mIndex = -1;
    private Calendar mStartNote;

    MonthAdapter(@NonNull Context context, Calendar calendar, Calendar startNote) {
        super(context);
        mCalendar = calendar;
        mStartNote = startNote;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.calendar_view_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isDayOfMonth(position)) {
            Calendar calendarDay = (Calendar) mCalendar.clone();
            calendarDay.set(Calendar.DATE, getDay(position));
            holder.mTvDay.setText(String.valueOf(getDay(position)));
        } else {
            holder.mTvDay.setBackground(null);
        }
    }

    private boolean isDayOfMonth(int position) {
        return position < TimeUtil.getDaysYearMonth(mCalendar)
                + TimeUtil.getDayPositionOffset(mCalendar)
                && position >= TimeUtil.getDayPositionOffset(mCalendar);
    }

    private int getDay(int position) {
        return position - TimeUtil.getDayPositionOffset(mCalendar) + 1;
    }

    @Override
    public int getItemCount() {
        return getSize();
    }

    private int getSize() {
        int dayAddOffset = TimeUtil.getDaysYearMonth(mCalendar) + TimeUtil.getDayPositionOffset(mCalendar);
        float row = (float) dayAddOffset / NUMBER_DAY_OF_WEEK;
        if (row <= 4) {
            return 4 * NUMBER_DAY_OF_WEEK;
        }
        if (row > 4 && row <= 5) {
            return 5 * NUMBER_DAY_OF_WEEK;
        }
        return 6 * NUMBER_DAY_OF_WEEK;
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvDay;
        private LinearLayout mLlDay;

        ViewHolder(View itemView) {
            super(itemView);
            mTvDay = (TextView) itemView.findViewById(R.id.tvDay);
            mLlDay = (LinearLayout) itemView.findViewById(R.id.llDay);
        }
    }
}
