package jp.welby.pah.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import jp.welby.pah.GoogleAnalyticsManager;
import jp.welby.pah.R;
import jp.welby.pah.model.calendar.DateData;
import jp.welby.pah.ui.BaseAdapter;
import jp.welby.pah.util.TimeUtil;

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

    public static final int DAY_OLD = 2;
    public static final int DAY_FUTURE = 1;
    public static final int DAY_NORMAL = 0;
    private static final int NUMBER_DAY_OF_WEEK = 7;

    private Calendar mCalendar;
    private List<DateData> mDateDataList;
    private OnItemClickListener mOnItemClickListener;
    private int mIndex = -1;
    private int mType;
    private Calendar mStartNote;
    private long mLastClickTime = 0;

    MonthAdapter(@NonNull Context context, int type, Calendar calendar, Calendar startNote, List<DateData> dateDataList, OnItemClickListener onItemClickListener) {
        super(context);
        mCalendar = calendar;
        mDateDataList = dateDataList;
        mOnItemClickListener = onItemClickListener;
        mType = type;
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
            DateData data = getDateData(calendarDay);
            holder.mImgSymptomNote.setVisibility(data != null && data.isSymptomNote()
                    ? View.VISIBLE : View.GONE);
            holder.mImgMedicalNote.setVisibility(data != null && data.isMedicalNote()
                    ? View.VISIBLE : View.GONE);
            holder.mImgSymptomLog.setVisibility(data != null && data.isSymptomLog()
                    ? View.VISIBLE : View.GONE);
            holder.mImgTakeMedicine.setVisibility(data != null && data.isTakenMedicine()
                    ? View.VISIBLE : View.GONE);
            if (mType == DAY_FUTURE && calendarDay.compareTo(Calendar.getInstance()) <= 0) {
                holder.mTvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorViewInactive));
            } else if (mType == DAY_OLD && (calendarDay.compareTo(Calendar.getInstance()) > 0 || calendarDay.compareTo(mStartNote) < 0)) {
                holder.mTvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorViewInactive));
            } else {
                holder.mTvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextViewDayCalendar));
            }
            if (TimeUtil.isToday(calendarDay) && data != null && data.isMedical()) {
                holder.mTvDay.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_calendar_view_today_and_consultation_day));
                holder.mTvDay.setTextColor(Color.WHITE);
            } else if (TimeUtil.isToday(calendarDay) || mIndex == position) {
                holder.mTvDay.setBackground(ContextCompat.getDrawable(getContext()
                        , R.drawable.bg_calendar_view_today));
                holder.mTvDay.setTextColor(Color.WHITE);
            } else if (data != null && data.isMedical()) {
                holder.mTvDay.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_calendar_view_examination_date));
                holder.mTvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextViewDayCalendar));
            } else {
                holder.mTvDay.setBackground(null);
            }
        } else {
            holder.mTvDay.setBackground(null);
            holder.mTvDay.setText(null);
            holder.mImgMedicalNote.setVisibility(View.GONE);
            holder.mImgSymptomLog.setVisibility(View.GONE);
            holder.mImgTakeMedicine.setVisibility(View.GONE);
            holder.mImgSymptomNote.setVisibility(View.GONE);
        }
    }

    private DateData getDateData(Calendar calendar) {
        for (DateData data : mDateDataList) {
            if (TimeUtil.isCompareDate(calendar, TimeUtil.getCalendar(data.getDate()))) {
                return data;
            }
        }
        return null;
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
        private ImageView mImgMedicalNote;
        private ImageView mImgTakeMedicine;
        private ImageView mImgSymptomLog;
        private ImageView mImgSymptomNote;

        ViewHolder(View itemView) {
            super(itemView);
            mTvDay = (TextView) itemView.findViewById(R.id.tvDay);
            mImgMedicalNote = (ImageView) itemView.findViewById(R.id.imgMedicalNote);
            mImgTakeMedicine = (ImageView) itemView.findViewById(R.id.imgTakeMedicine);
            mImgSymptomLog = (ImageView) itemView.findViewById(R.id.imgSymptomLog);
            mImgSymptomNote = (ImageView) itemView.findViewById(R.id.imgSymptomNote);
            mLlDay = (LinearLayout) itemView.findViewById(R.id.llDay);

            mLlDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoogleAnalyticsManager.getInstance().sendEvent(R.string.trackers_screen_name_calendar, R.string.trackers_bt_label_name_day_tap);
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    if (isDayOfMonth(getAdapterPosition())) {
                        Calendar calendarDay = (Calendar) mCalendar.clone();
                        calendarDay.set(Calendar.DATE, getDay(getAdapterPosition()));
                        mOnItemClickListener.onDayClick(calendarDay);
                        mIndex = getAdapterPosition();
                        if (mType == DAY_FUTURE && calendarDay.compareTo(Calendar.getInstance()) > 0 || TimeUtil.isCompareDate(Calendar.getInstance(), calendarDay)) {
                            mOnItemClickListener.onDayClick(calendarDay);
                            mIndex = getAdapterPosition();
                        } else if (mType == DAY_OLD && calendarDay.compareTo(Calendar.getInstance()) <= 0 && calendarDay.compareTo(mStartNote) >= 0) {
                            mOnItemClickListener.onDayClick(calendarDay);
                            mIndex = getAdapterPosition();
                        } else if (mType == DAY_NORMAL) {
                            mOnItemClickListener.onDayClick(calendarDay);
                            mIndex = getAdapterPosition();
                        }
                        MonthAdapter.this.notifyItemChanged(getAdapterPosition());
                    }
                }
            });
        }
    }
}
