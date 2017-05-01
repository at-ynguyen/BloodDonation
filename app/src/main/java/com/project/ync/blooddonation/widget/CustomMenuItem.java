package com.project.ync.blooddonation.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.ync.blooddonation.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author YNC
 */
@EViewGroup(R.layout.item_menu)
public class CustomMenuItem extends RelativeLayout {
    private String mTitle;
    @ViewById(R.id.tvTitle)
    TextView mTvTitle;

    public CustomMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMenuItem);
        mTitle = typedArray.getString(R.styleable.CustomMenuItem_title);
        typedArray.recycle();
    }

    @AfterViews
    void init() {
        mTvTitle.setText(mTitle);
    }
}
