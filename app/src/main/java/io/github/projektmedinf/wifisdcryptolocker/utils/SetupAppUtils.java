package io.github.projektmedinf.wifisdcryptolocker.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by bgeVam on 16.04.17.
 */
public class SetupAppUtils {

    private final static String SETUP_COMPLETED_SHARED_PREFERENCE = "setup_completed";

    /**
     * Checks if setup is already completed
     *
     * @param activity the activity for the shared preferences
     * @return true if setup is completed, else false
     */
    public static boolean isSetupCompleted(Activity activity) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
        return sharedPref.getBoolean(SETUP_COMPLETED_SHARED_PREFERENCE, false);
    }

    /**
     * Sets the setup completed in the preferences
     *
     * @param activity the activity for the shared preferences
     */
    public static void setSetupCompleted(Activity activity) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
        sharedPref.edit().putBoolean(SETUP_COMPLETED_SHARED_PREFERENCE, true).apply();
    }
}
