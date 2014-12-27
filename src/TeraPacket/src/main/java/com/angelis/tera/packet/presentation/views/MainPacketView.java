package com.angelis.tera.packet.presentation.views;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.angelis.tera.packet.presentation.controllers.PacketController;

public class MainPacketView extends Application {

    public static MainPacketView instance;
    
    private final FXMLLoader fxmlLoader;
    
    
    public MainPacketView() {
        fxmlLoader = new FXMLLoader(getClass().getResource("/com/angelis/tera/packet/presentation/views/"+this.getClass().getSimpleName()+".fxml"));
        MainPacketView.instance = this;
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final Parent root = (Parent) fxmlLoader.load();
        
        getController().loadUi();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent we) {
                System.exit(0);
            }
        });  
        stage.setScene(new Scene(root));
        stage.show();
    }

    public PacketController getController() {
        return (PacketController) fxmlLoader.getController();
    }
}
