package com.angelis.tera.launcher;

import javafx.application.Application;

import com.angelis.tera.launcher.presentation.LauncherWindow;
import com.angelis.tera.launcher.services.ConfigService;
import com.angelis.tera.launcher.services.I18nService;

public class MainLauncher {
    public static void main(final String[] args) {
        ConfigService.getInstance().start();
        I18nService.getInstance().start();

        Application.launch(LauncherWindow.class, args);
    }
}
