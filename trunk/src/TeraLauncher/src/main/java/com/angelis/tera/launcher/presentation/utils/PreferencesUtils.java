package com.angelis.tera.launcher.presentation.utils;

import java.util.prefs.Preferences;

import com.angelis.tera.launcher.presentation.controllers.LoginController;

public class PreferencesUtils {
    
    private static final Preferences preferences = Preferences.userRoot().node(LoginController.class.getName());
    
    public static final String get(final String key, final String defaultValue) {
        return preferences.get(key, defaultValue);
    }

    public static final void put(final String key, final String value) {
        preferences.put(key, value);
    }
}
