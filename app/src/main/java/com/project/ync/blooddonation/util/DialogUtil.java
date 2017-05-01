package com.project.ync.blooddonation.util;

import android.content.Context;
import android.view.WindowManager;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author YNC
 */
public final class DialogUtil {
    private DialogUtil() {
    }

    public static void createErrorDialog(Context context, String title, String message) {
        try {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText(title)
                    .setContentText(message)
                    .setConfirmText("OK");

            sweetAlertDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public static void createErrorDialog(SweetAlertDialog.OnSweetClickListener onClickConfirm, Context context, String title, String message) {
        try {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText(title)
                    .setContentText(message)
                    .setConfirmText("OK");
            sweetAlertDialog.setConfirmClickListener(onClickConfirm);
            sweetAlertDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public static SweetAlertDialog createSuccessSecret(Context context, String title, String message) {
        try {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setTitleText(title)
                    .setContentText(message)
                    .setConfirmText("OK");
            return sweetAlertDialog;
        } catch (Exception e) {
            return null;
        }
    }

}
