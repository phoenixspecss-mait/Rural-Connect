package com.example.ruralconnect.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "RuralConnectSession";
    private static final String KEY_AUTH_TOKEN = "authToken";
    private static final String KEY_USER_ROLE = "userRole";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveAuthToken(String token) {
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
    }

    public void saveUserRole(String role) {
        editor.putString(KEY_USER_ROLE, role);
        editor.apply();
    }

    public String getAuthToken() {
        String token = prefs.getString(KEY_AUTH_TOKEN, null);
        if (token != null) {
            return "Bearer " + token;
        }
        return null;
    }

    public String getUserRole() {
        return prefs.getString(KEY_USER_ROLE, null);
    }

    public boolean isLoggedIn() {
        return getAuthToken() != null;
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}