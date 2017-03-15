package com.project.ync.blooddonation.ui;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by ynguyen on 15/03/2017.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {
    @AfterViews
    protected abstract void init();
}
