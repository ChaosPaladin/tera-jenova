package com.angelis.tera.packet.process.network.packet.handlers;

import javafx.concurrent.Task;

import com.angelis.tera.packet.presentation.views.MainWindow;
import com.angelis.tera.packet.process.network.packet.Packet;

public class UIPacketHandler extends AbstractPacketHandler {

    @Override
    public void handle(final Packet packet) {
        new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                MainWindow.instance.getController().getListView().getItems().add(packet);
                return null;
            }
            
        }.run();
    }
}
