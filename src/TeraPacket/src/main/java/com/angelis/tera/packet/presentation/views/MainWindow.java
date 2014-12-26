package com.angelis.tera.packet.presentation.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.angelis.tera.packet.presentation.controllers.PacketController;

public class MainWindow extends Application {

    public static MainWindow instance;
    
    private final FXMLLoader fxmlLoader;
    
    
    public MainWindow() {
        fxmlLoader = new FXMLLoader(getClass().getResource("/com/angelis/tera/packet/presentation/views/MainWindow.fxml"));
        MainWindow.instance = this;
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final Parent root = (Parent) fxmlLoader.load();
        
        getController().loadUi();
        
        stage.setScene(new Scene(root));
        stage.show();
    }

    public PacketController getController() {
        return (PacketController) fxmlLoader.getController();
    }
}
