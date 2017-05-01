package com.project.ync.blooddonation;

import android.app.Application;

import com.project.ync.blooddonation.api.core.ApiClient;
import com.project.ync.blooddonation.api.core.ApiConfig;

import org.androidannotations.annotations.EApplication;

/**
 * @author YNC
 */
@EApplication
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // retrofit
        createService();
    }

    private void createService() {
        ApiConfig apiConfig = ApiConfig.builder()
                .context(getApplicationContext())
                .baseUrl(getString(R.string.url_host))
                .build();
        ApiClient.getInstance().init(apiConfig);
    }
}
