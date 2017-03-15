package com.project.ync.blooddonation.util;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * TimeUtil.
 *
 * @author YNC
 */
public final class TimeUtil {
    public static final String FORMAT_YEAR_MONTH = "yyyy 年 M 月";
    public static final String DT_FORMAT_YEAR_MONTH_DAY = "yyyy 年 M 月 dd 日";
    public static final String DT_FORMAT_MONTH_DATE_MEDICINE_MEDICAL = "MM/dd";
    public static final String DT_FORMAT_YEAR_MONTH_DAY_HOME = "yyyy年MM月dd日";
    public static final String DT_FORMAT_MONTH_DATE = "MM月dd日";
    public static final String DT_FORMAT_YEAR_MONTH_DATE_SERVER = "yyyy-MM-dd";
    public static final String DT_FORMAT_HOUR_MINUTE = "H:mm";
    private static final String DT_FORMAT_YEAR_MONTH = "yyyy-MM";
    public static final String DT_FORMAT_MONTH_DATE_DEFAULT = "--月--日";
    private static final String TAG = "TimeUtil";

    private TimeUtil() {
    }

    public static String getTimeFormatFromTimeServer(String time, String dtFormat) {
        SimpleDateFormat myFormat = new SimpleDateFormat(DT_FORMAT_YEAR_MONTH_DATE_SERVER, Locale.JAPAN);
        SimpleDateFormat resultFormat = new SimpleDateFormat(dtFormat, Locale.JAPAN);
        try {
            return resultFormat.format(myFormat.parse(time));
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());

        }
        return DT_FORMAT_MONTH_DATE_DEFAULT;
    }

    public static String getTimeFromCal(Calendar calendar, String dtFormat) {
        return DateFormat.format(dtFormat, calendar).toString();
    }

    public static String getDateMedicineTable(int addMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, addMonth);
        return DateFormat.format(DT_FORMAT_YEAR_MONTH_DATE_SERVER, calendar).toString();
    }

    public static int getDaysYearMonth(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.DATE);
    }

    public static int getDayPositionOffset(Calendar calendar) {
        calendar.set(Calendar.DATE, 1);
        return (calendar.get(Calendar.DAY_OF_WEEK) + 6) % Calendar.DAY_OF_WEEK;
    }

    public static boolean isCompareDate(Calendar c1, Calendar c2) {
        return ((c1.get(Calendar.DATE) == c2.get(Calendar.DATE))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)));

    }

    public static boolean isToday(Calendar calendar) {
        return isCompareDate(calendar, Calendar.getInstance());
    }

    @NonNull
    public static String getFormattedYearMonth(@NonNull Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_YEAR_MONTH, Locale.JAPAN);
        return dateFormat.format(calendar.getTime());
    }

    @NonNull
    public static String getYearMonth(@NonNull Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DT_FORMAT_YEAR_MONTH, Locale.JAPAN);
        return dateFormat.format(calendar.getTime());
    }

    @NonNull
    public static String getDate(@NonNull Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DT_FORMAT_YEAR_MONTH_DATE_SERVER, Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    @NonNull
    public static Calendar getCalendar(@NonNull String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(date));
            return calendar;
        } catch (ParseException e) {
            throw new RuntimeException("Illegal date: " + date, e);
        }
    }

    @NonNull
    public static int getNumberMonthBetWeenTwoDays(Calendar c1, Calendar c2) {
        int diffYear = Math.abs(c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR));
        return diffYear * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
    }
}
