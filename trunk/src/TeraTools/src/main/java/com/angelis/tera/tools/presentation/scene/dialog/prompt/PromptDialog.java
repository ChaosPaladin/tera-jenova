package com.angelis.tera.tools.presentation.scene.dialog.prompt;

import java.util.function.Function;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.angelis.tera.tools.presentation.scene.dialog.AbstractDialog;

public class PromptDialog extends AbstractDialog {

    public final static Function<Void, Void> emptyCancelCallback = new Function<Void, Void>() {
        @Override
        public Void apply(final Void t) {
            return null;
        }
    };
    
    private final Function<String, Boolean> confirmCallback;
    private final Function<Void, Void> cancelCallback;
    
    @FXML
    private Label promptLabel;
    
    public PromptDialog(final Function<String, Boolean> confirmCallback, final Function<Void, Void> cancelCallback, final String promptText) {
        super("promptdialog.fxml");
        this.confirmCallback = confirmCallback;
        this.cancelCallback = cancelCallback;
        
        this.promptLabel.setText(promptText);
    }
    
    public PromptDialog(final Function<String, Boolean> confirmCallback, final String prompText) {
        this(confirmCallback, emptyCancelCallback, prompText);
    }
    
    public void onCancel() {
        cancelCallback.apply(null);
        this.close();
    }

    public void onConfirm() {
        if (confirmCallback.apply(this.promptLabel.getText())) {
            this.close();
        }
    }
}
