package com.project.ync.blooddonation.util;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * TimeUtil.
 *
 * @author YNC
 */
public final class TimeUtil {
    public static final String FORMAT_YEAR_MONTH = " MM/yyyy";
    public static final String DT_FORMAT_YEAR_MONTH_DAY = "dd-MM-yyyy";
    public static final String DT_FORMAT_MONTH_DATE_MEDICINE_MEDICAL = "MM/dd";
    public static final String DT_FORMAT_YEAR_MONTH_DAY_HOME = "yyyy年MM月dd日";
    public static final String DT_FORMAT_MONTH_DATE = "MM月dd日";
    public static final String DT_FORMAT_YEAR_MONTH_DATE_SERVER = "yyyy-MM-dd";
    public static final String DT_FORMAT_HOUR_MINUTE = "H:mm";
    private static final String DT_FORMAT_YEAR_MONTH = "yyyy-MM";
    private static final String TAG = "TimeUtil";

    private TimeUtil() {
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

    public static String convertTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return getTimeFromCal(calendar, DT_FORMAT_YEAR_MONTH_DAY);
    }

    public static Date convertDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Illegal date: " + date, e);
        }
    }


    public static String parseDate(long timeAtMiliseconds) {
        if (timeAtMiliseconds <= 0) {
            return "";
        }
        String result = "Vừa xong.";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataSot = formatter.format(new Date());
        Calendar calendar = Calendar.getInstance();

        long dayagolong = timeAtMiliseconds;
        calendar.setTimeInMillis(dayagolong);

        String agoformater = formatter.format(calendar.getTime());

        Date CurrentDate = null;
        Date CreateDate = null;

        try {
            CurrentDate = formatter.parse(dataSot);
            CreateDate = formatter.parse(agoformater);

            long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            different = different % secondsInMilli;
            if (elapsedDays == 0) {
                if (elapsedHours == 0) {
                    if (elapsedMinutes == 0) {
                        if (elapsedSeconds < 0) {
                            return "0" + " s";
                        } else {
                            if (elapsedDays > 0 && elapsedSeconds < 59) {
                                return "Vừa xong.";
                            }
                        }
                    } else {
                        return String.valueOf(elapsedMinutes) + " phút trước.";
                    }
                } else {
                    return String.valueOf(elapsedHours) + " giờ trước";
                }

            } else {
                if (elapsedDays <= 29) {
                    return String.valueOf(elapsedDays) + " ngày trước";
                }
                if (elapsedDays > 29 && elapsedDays <= 58) {
                    return "1 tháng trước";
                }
                if (elapsedDays > 58 && elapsedDays <= 87) {
                    return "2 tháng trước";
                }
                if (elapsedDays > 87 && elapsedDays <= 116) {
                    return "3 tháng trước";
                }
                if (elapsedDays > 116 && elapsedDays <= 145) {
                    return "4 tháng trước";
                }
                if (elapsedDays > 145 && elapsedDays <= 174) {
                    return "5 tháng trước";
                }
                if (elapsedDays > 174 && elapsedDays <= 203) {
                    return "6 tháng trước";
                }
                if (elapsedDays > 203 && elapsedDays <= 232) {
                    return "7 tháng trước";
                }
                if (elapsedDays > 232 && elapsedDays <= 261) {
                    return "8 tháng trước";
                }
                if (elapsedDays > 261 && elapsedDays <= 290) {
                    return "9 tháng trước";
                }
                if (elapsedDays > 290 && elapsedDays <= 319) {
                    return "10 tháng trước";
                }
                if (elapsedDays > 319 && elapsedDays <= 348) {
                    return "11 tháng trước";
                }
                if (elapsedDays > 348 && elapsedDays <= 360) {
                    return "12 tháng trước";
                }

                if (elapsedDays > 360 && elapsedDays <= 720) {
                    return "1 năm trước";
                }

                if (elapsedDays > 720) {
                    SimpleDateFormat formatterYear = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar calendarYear = Calendar.getInstance();
                    calendarYear.setTimeInMillis(dayagolong);
                    return formatterYear.format(calendarYear.getTime()) + "";
                }

            }

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
