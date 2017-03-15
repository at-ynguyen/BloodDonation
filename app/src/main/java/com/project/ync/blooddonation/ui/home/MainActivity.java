package com.project.ync.blooddonation.ui.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.ync.blooddonation.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @AfterViews
    void init() {
    }
}
