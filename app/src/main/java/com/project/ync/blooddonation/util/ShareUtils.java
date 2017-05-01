package com.project.ync.blooddonation.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * @author YNC
 */
public final class ShareUtils {
    private ShareUtils() {
    }

    public void SharingToSocialMedia(String application, String text, Context context) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, text);

        boolean installed = checkAppInstall(context, application);
        if (installed) {
            intent.setPackage(application);
            context.startActivity(intent);
        } else {
        }

    }


    private boolean checkAppInstall(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

}
