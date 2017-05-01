package com.project.ync.blooddonation.ui.history;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.ui.BaseFragment;
import com.project.ync.blooddonation.util.TimeUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

/**
 * @author YNC
 */
@EFragment(R.layout.fragment_calendar_history)
public class HistoryCalendarFragment extends BaseFragment {
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;
    @ViewById(R.id.tvMonthOfYear)
    TextView mTvMonthOfYear;

    private MonthPagerAdapter mAdapter;
    private Calendar mCalendar;

    @Override
    protected void init() {
        mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.YEAR, -1000);
        loadCalendar();
    }

    @Override
    public void loadData() {
    }

    public void loadCalendar() {
        mAdapter = new MonthPagerAdapter(getChildFragmentManager(), mCalendar);
        mTvMonthOfYear.setText(TimeUtil.getFormattedYearMonth(Calendar.getInstance()));
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(TimeUtil.getNumberMonthBetWeenTwoDays(mCalendar, Calendar.getInstance()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvMonthOfYear.setText(mAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Click(R.id.imgNextMonth)
    void onClickNextMonth() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    @Click(R.id.imgPrevMonth)
    void onClickPrevMonth() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    @Click(R.id.tvView)
    void onClickView() {
        replaceFragment(DetailHistoryFragment_.builder().build(), R.id.flContainer, false);
    }
}
