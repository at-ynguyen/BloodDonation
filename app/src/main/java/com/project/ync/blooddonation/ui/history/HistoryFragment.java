package com.project.ync.blooddonation.ui.history;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.ui.BaseFragment;

import org.androidannotations.annotations.EFragment;

/**
 * @author YNC
 */
@EFragment(R.layout.fragment_history)
public class HistoryFragment extends BaseFragment {
    @Override
    protected void init() {
    }

    @Override
    public void loadData() {
        replaceFragment(DetailHistoryFragment_.builder().build());
    }
}
