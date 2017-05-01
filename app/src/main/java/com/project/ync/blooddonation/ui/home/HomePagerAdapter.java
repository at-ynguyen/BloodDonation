package com.project.ync.blooddonation.ui.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.project.ync.blooddonation.ui.award.AwardFragment_;
import com.project.ync.blooddonation.ui.history.HistoryFragment_;
import com.project.ync.blooddonation.ui.menu.MenuFragment_;

/**
 * @author YNC
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUMBER_PAGER = 4;
    private static final int HOME = 0;
    private static final int HISTORY = 1;
    private static final int AWARD = 2;
    private static final int MENU = 3;
    private SparseArray<Fragment> mListFragment = new SparseArray<>();

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case HOME:
                frag = HomeFragment_.builder().build();
                mListFragment.put(HOME, frag);
                break;
            case HISTORY:
                frag = HistoryFragment_.builder().build();
                mListFragment.put(HISTORY, frag);
                break;
            case AWARD:
                frag = AwardFragment_.builder().build();
                mListFragment.put(AWARD, frag);
                break;
            case MENU:
                frag = MenuFragment_.builder().build();
                mListFragment.put(MENU, frag);
                break;

        }
        return frag;
    }

    public Fragment getFragment(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return NUMBER_PAGER;
    }
}
