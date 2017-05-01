package com.project.ync.blooddonation.shareds;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * @author YNC
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface SharedPreferences {
    @DefaultString("")
    String accessToken();

    @DefaultString("")
    String tokenNotification();
}
