package com.project.ync.blooddonation.ui.history;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.ui.BaseFragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

/**
 * @author YNC
 */
@EFragment(R.layout.fragment_month)
public class MonthFragment extends BaseFragment {
    private static final int NUMBER_DAY_OF_WEEK = 7;

    @FragmentArg
    Calendar mCalendar;
    @FragmentArg
    int mType;
    @FragmentArg
    Calendar mStartNote;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MonthAdapter mAdapter;
    private Handler mHandler = new Handler();

    @Override
    protected void init() {
        mAdapter = new MonthAdapter(getActivity(), mCalendar, mStartNote);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUMBER_DAY_OF_WEEK));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacksAndMessages(null);
    }
}
