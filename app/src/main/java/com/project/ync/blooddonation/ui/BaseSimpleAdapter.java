package com.project.ync.blooddonation.ui;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by ynguyen on 15/03/2017.
 */

public abstract class BaseSimpleAdapter<T, VH extends BaseHolder> extends BaseAdapter<VH> {
    /**
     * Main DataSet.
     * <p>
     * Getting item of list by {@link BaseSimpleAdapter#getItem(int)}
     */
    private final List<T> mObjects;

    public BaseSimpleAdapter(@NonNull Context context, @NonNull List<T> ts) {
        super(context);
        mObjects = ts;
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    protected T getItem(int index) {
        return mObjects.get(index);
    }

    @NonNull
    protected List<T> getDataList() {
        return mObjects;
    }
}