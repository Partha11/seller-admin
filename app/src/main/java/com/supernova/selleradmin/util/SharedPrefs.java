package com.supernova.selleradmin.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SharedPrefs(Context context) {

        this.prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setIsFirstInstall(boolean value) {

        editor = prefs.edit();

        editor.putBoolean(Constants.PREF_FIRST_INSTALL, value);
        editor.apply();
    }

    public boolean isFirstInstall() {

        return prefs.getBoolean(Constants.PREF_FIRST_INSTALL, false);
    }

    public void setIsLoggedIn(boolean value) {

        editor = prefs.edit();

        editor.putBoolean(Constants.PREF_LOGGED_IN, value);
        editor.apply();
    }

    public boolean isLoggedIn() {

        return prefs.getBoolean(Constants.PREF_LOGGED_IN, false);
    }

    public void setUserEmail(String email) {

        editor = prefs.edit();

        editor.putString(Constants.USER_EMAIL, email);
        editor.apply();
    }

    public String getUserEmail() {

        return prefs.getString(Constants.USER_EMAIL, "");
    }

    public void setUserToken(String token) {

        editor = prefs.edit();

        editor.putString(Constants.USER_TOKEN, token);
        editor.apply();
    }

    public String getUserToken() {

        return prefs.getString(Constants.USER_TOKEN, "");
    }
}
