package com.project.ync.blooddonation.ui.home;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.ui.BaseFragment;

import org.androidannotations.annotations.EFragment;

/**
 * @author YNC
 */
@EFragment(R.layout.fragment_home_container)
public class HomeFragmentContainer extends BaseFragment {
    @Override
    protected void init() {

    }

    @Override
    public void loadData() {
        replaceFragment(HomeFragment_.builder().build());
    }
}
