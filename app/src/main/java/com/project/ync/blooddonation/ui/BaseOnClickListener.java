package com.project.ync.blooddonation.ui;

import android.view.View;

/**
 * Created by ynguyen on 15/03/2017.
 */

public abstract class BaseOnClickListener implements View.OnClickListener {
    private static final int MIN_CLICK_INTERVAL = 500;

    private static long sLastClickTime;

    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        if (isBlockingClick()) {
            return;
        }
        onSingleClick(v);
    }

    public static boolean isBlockingClick() {
        return isBlockingClick(MIN_CLICK_INTERVAL);
    }

    public static boolean isBlockingClick(long minClickInterval) {
        boolean isBlocking;
        long currentTime = System.currentTimeMillis();
        isBlocking = currentTime - sLastClickTime < minClickInterval;
        if (!isBlocking) {
            sLastClickTime = currentTime;
        }
        return isBlocking;
    }
}

