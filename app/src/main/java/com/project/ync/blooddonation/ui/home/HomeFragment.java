package com.project.ync.blooddonation.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.ui.BaseFragment;
import com.project.ync.blooddonation.ui.event.EventPagerAdapter;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * @author YNC
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {
    @ViewById(R.id.tabLayout)
    TabLayout mTabLayout;
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void init() {
        mTabLayout.setupWithViewPager(mViewPager);
        EventPagerAdapter eventPagerAdapter = new EventPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(eventPagerAdapter);
        setUpTabLayout();
    }

    @Override
    public void loadData() {
    }

    private void setUpTabLayout() {
        TextView tvHotBlood = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout, null);
        tvHotBlood.setText("Khẩn cấp");
        tvHotBlood.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_hot, 0, 0);
        mTabLayout.getTabAt(0).setCustomView(tvHotBlood);

        TextView tvNotify = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tablayout, null);
        tvNotify.setText("Đợt hiến");
        tvNotify.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_notify, 0, 0);
        mTabLayout.getTabAt(1).setCustomView(tvNotify);
    }
}
