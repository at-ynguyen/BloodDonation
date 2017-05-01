package com.project.ync.blooddonation.ui.event;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.ui.BaseFragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

/**
 * @author YNC
 */
@EFragment(R.layout.fragment_detail_event)
public class DetailEventFragment extends BaseFragment {

    @FragmentArg
    int mId;

    @Override
    protected void init() {

    }

    @Override
    public void loadData() {

    }
}
