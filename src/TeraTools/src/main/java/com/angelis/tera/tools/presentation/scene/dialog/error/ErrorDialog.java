package com.angelis.tera.tools.presentation.scene.dialog.error;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.angelis.tera.tools.presentation.scene.dialog.AbstractDialog;

public class ErrorDialog extends AbstractDialog {
    
    @FXML
    private Label errorLabel;
    
    public ErrorDialog(final String errorText) {
        super("errordialog.fxml");
        this.errorLabel.setText(errorText);
    }
    
    public void onConfirm() {
        this.close();
    }
}
