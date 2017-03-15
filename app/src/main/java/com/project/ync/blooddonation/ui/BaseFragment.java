package com.project.ync.blooddonation.ui;

import android.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by ynguyen on 15/03/2017.
 */
@EFragment
public abstract class BaseFragment extends Fragment {
    @AfterViews
    protected abstract void init();
}
