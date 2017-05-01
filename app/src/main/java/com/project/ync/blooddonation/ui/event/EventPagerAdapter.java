package com.project.ync.blooddonation.ui.event;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

/**
 * @author YNC
 */
public class EventPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUMBER_PAGER = 2;

    public EventPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = FindBloodFragment_.builder().build();
                break;
            case 1:
                frag = EventFragment_.builder().build();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return NUMBER_PAGER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Máu nóng";
        }
        return "Đợt hiến máu";
    }
}
