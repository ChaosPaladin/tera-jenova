package com.angelis.tera.tools.presentation.scene.dialog;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public abstract class AbstractDialog extends Popup {

    protected final Parent parent;
    protected Stage stage;
    
    public AbstractDialog(final String fxmlFileName) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.parent = fxmlLoader.getRoot();
    }
    
    @Override
    public void show(final  Window root) {
        this.initScene(root);
    }
    
    public void close() {
        this.stage.close();
    }
    
    protected void initScene(final Window root) {
        this.stage = new Stage(StageStyle.DECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner(root);
        final Scene scene = new Scene(parent);
       
        stage.setScene(scene);
        stage.show();
    }
}
