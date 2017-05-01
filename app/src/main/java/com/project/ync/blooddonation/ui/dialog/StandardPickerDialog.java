package com.project.ync.blooddonation.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.project.ync.blooddonation.R;
import com.project.ync.blooddonation.util.ScreenUtil;
import com.project.ync.blooddonation.widget.PickerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author YNC
 */
@EFragment(R.layout.dialog_standard_picker)
public class StandardPickerDialog extends DialogFragment {
    @FragmentArg
    int mResourceArray;

    public interface IListenerStandardPicker {
        void onClickOk(String item, int index);

        void onDismiss();
    }

    @ViewById(R.id.pickerView)
    PickerView mPickerView;

    private IListenerStandardPicker mListenerStandardPicker;
    private List<String> mDataLists = new ArrayList<>();

    @AfterViews
    void afterView() {
        if (mResourceArray != 0) {
            final String[] bloodType = getResources().getStringArray(mResourceArray);
            mDataLists.addAll(Arrays.asList(bloodType));
            mPickerView.setDataList(new ArrayList<>(mDataLists));
            mPickerView.setCanLoop(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * Set width & height of dialog on onResume method follow this
         * http://stackoverflow.com/questions/14946887/setting-the-size-of-a-dialogfragment
         */
        if (getDialog().getWindow() != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = getWidthDialog();
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes(params);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        if (dialog.getWindow() != null) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }

    @Click(R.id.tvOk)
    void onClickOk() {
        if (mListenerStandardPicker != null) {
            mListenerStandardPicker.onClickOk(mDataLists.get(mPickerView.getSelectedItem()),
                    mPickerView.getSelectedItem());
        }
        dismiss();
    }

    @Click(R.id.tvCancel)
    void onClickCancel() {
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListenerStandardPicker != null) {
            mListenerStandardPicker.onDismiss();
        }
    }

    public StandardPickerDialog setListenerStandardPicker(IListenerStandardPicker listenerStandardPicker) {
        this.mListenerStandardPicker = listenerStandardPicker;
        return this;
    }

    protected int getWidthDialog() {
        return ScreenUtil.getWidthScreen(getActivity()) * 80 / 100;
    }
}
