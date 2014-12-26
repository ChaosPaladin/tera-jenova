package com.angelis.tera.packet;

import javafx.application.Application;
import javafx.application.Platform;

import com.angelis.tera.packet.presentation.views.MainWindow;
import com.angelis.tera.packet.process.services.ConfigService;
import com.angelis.tera.packet.process.services.NetworkService;

public class MainPacket {
    public static void main(final String[] args) {
        ConfigService.getInstance().start();
        NetworkService.getInstance().start();
        
        Platform.setImplicitExit(false);
        Application.launch(MainWindow.class, args);
    }
}
