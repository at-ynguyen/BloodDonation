package com.project.ync.blooddonation.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.project.ync.blooddonation.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by ynguyen on 15/03/2017.
 */
@EFragment
public abstract class BaseFragment extends Fragment {
    @AfterViews
    protected abstract void init();

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        Fragment fragmentCurrent = fm.findFragmentById(R.id.flContainer);
        if (fragmentCurrent != null && fragment.getClass().getSimpleName().equals(fragmentCurrent.getTag())) {
            return;
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.flContainer, fragment, fragment.getClass().getSimpleName());
        ft.addToBackStack(null);
        if (!isRemoving()) {
            ft.commitAllowingStateLoss();
        }
    }

    public void replaceFragment(Fragment fragment, int idContainer, boolean addToBackStack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.replace(idContainer, fragment, fragment.getClass().getSimpleName());
        if (!isRemoving()) {
            ft.commit();
        }
        fm.executePendingTransactions();
    }

    public abstract void loadData();
}
