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

    public void setBkash(String number) {

        editor = prefs.edit();

        editor.putString(Constants.BKASH_AGENT, number);
        editor.apply();
    }

    private String getBkash() {

        return prefs.getString(Constants.BKASH_AGENT, "");
    }

    public void setNogod(String number) {

        editor = prefs.edit();

        editor.putString(Constants.NOGOD_AGENT, number);
        editor.apply();
    }

    private String getNogod() {

        return prefs.getString(Constants.NOGOD_AGENT, "");
    }

    public void setRocket(String number) {

        editor = prefs.edit();

        editor.putString(Constants.ROCKET, number);
        editor.apply();
    }

    private String getRocket() {

        return prefs.getString(Constants.ROCKET, "");
    }

    public void setChipsPrice(String price) {

        editor = prefs.edit();

        editor.putString(Constants.CHIPS_PRICE, price);
        editor.apply();
    }

    public String getChipsPrice() {

        return prefs.getString(Constants.CHIPS_PRICE, "70");
    }

    public String getNumber(int i) {

        switch(i) {
            case 0:
                return getBkash();
            case 1:
                return getNogod();
            case 2:
                return getRocket();
            default:
                return "";
        }
    }

    public void setNumber(int position, String text) {

        switch(position) {
            case 0:
                setBkash(text);
            case 1:
                setNogod(text);
            case 2:
                setRocket(text);
        }
    }
}
