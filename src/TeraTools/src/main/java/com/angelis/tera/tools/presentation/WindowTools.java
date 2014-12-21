package com.angelis.tera.tools.presentation;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.angelis.tera.tools.extractor.MainCreatureExtractor;
import com.angelis.tera.tools.presentation.scene.dialog.error.ErrorDialog;
import com.angelis.tera.tools.presentation.scene.dialog.prompt.PromptDialog;

public class WindowTools extends Application {
    
    private Stage stage;
    
    @Override
    public void start(final Stage stage) throws IOException {
        this.stage = stage;
        
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("application.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        
        stage.setTitle("Tera Tools");
        stage.setScene(new Scene(fxmlLoader.getRoot(), 300, 275));
        stage.show();
    }

    @FXML
    public void onCreatureParse(final ActionEvent event) {
        final PromptDialog prompt = new PromptDialog(new Function<String, Boolean>() {
            @Override
            public Boolean apply(final String t) {
                final File file = new File(t);
                if (!file.exists()) {
                    final ErrorDialog dialog = new ErrorDialog("Impossible de trouver ce fichier");
                    dialog.show(WindowTools.this.stage);
                    return false;
                }
                
                try {
                    new Thread(new MainCreatureExtractor(file)).start();
                    return true;
                }
                catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Indiquez le chemin vers le fichier log.txt");
        prompt.show(this.stage);
    }
}
