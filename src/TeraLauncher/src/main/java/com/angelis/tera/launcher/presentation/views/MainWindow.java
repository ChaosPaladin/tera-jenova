package com.angelis.tera.launcher.presentation.views;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.angelis.tera.launcher.presentation.controllers.LoginController;

public class MainWindow extends Application {

    private final FXMLLoader fxmlLoader;
    
    private double xOffset;
    private double yOffset;
    
    public MainWindow() {
        fxmlLoader = new FXMLLoader(getClass().getResource("/com/angelis/tera/launcher/presentation/views/MainWindow.fxml"));
    }
    
    @Override
    public void start(final Stage stage) throws Exception {
        final Parent root = (Parent) fxmlLoader.load();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        
        getController().loadPreferences();
        getController().loadUi();
        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Tera launcher");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public LoginController getController() {
        return (LoginController) fxmlLoader.getController();
    }
}
