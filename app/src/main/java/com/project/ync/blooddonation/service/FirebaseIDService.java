package com.project.ync.blooddonation.service;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.androidannotations.annotations.EBean;

/**
 * @author YNC
 */
@EBean
public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "onTokenRefresh: " + refreshedToken);
        SharedPreferences mSharedPreferences = getSharedPreferences("PUSHNOTIFICATION", 0);
        String oldToken = mSharedPreferences.getString("Token", null);

        if (oldToken == null) {
            Log.d("TAG1111", "oldToken: " + null);
        }

        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("Token", refreshedToken);
        mEditor.commit();

    }
}
