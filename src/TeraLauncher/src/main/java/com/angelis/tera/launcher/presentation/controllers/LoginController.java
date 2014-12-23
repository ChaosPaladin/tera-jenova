package com.angelis.tera.launcher.presentation.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebView;
import javolution.util.FastList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.LocaleUtils;

import com.angelis.tera.common.utils.Function;
import com.angelis.tera.launcher.config.FileConfig;
import com.angelis.tera.launcher.config.LoginConfig;
import com.angelis.tera.launcher.presentation.json.results.ResultObject;
import com.angelis.tera.launcher.presentation.utils.HTTPUtils;
import com.angelis.tera.launcher.presentation.utils.PreferencesUtils;
import com.angelis.tera.launcher.services.I18nService;
import com.google.gson.Gson;

public class LoginController {
    
    @FXML
    private ComboBox<String> langCombo;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button playButton;
    
    @FXML
    private WebView webView;
    
    private boolean loggedIn;

    @FXML
    public void onLoginClick(final ActionEvent event) throws MalformedURLException {
        final String login = loginTextField.getText();
        final String password = passwordTextField.getText();
        final String locale = langCombo.getValue();

        final URL url = new URL("HTTP", LoginConfig.LOGIN_URL, LoginConfig.LOGIN_PORT, "/login?login=" + login + "&password=" + password+"&locale="+locale);
        HTTPUtils.doGet(url, new Function<InputStream>() {
            @Override
            public void call(final InputStream argument) {
                final Gson gson = new Gson();
                final BufferedReader in = new BufferedReader(new InputStreamReader(argument));
                final ResultObject obj = gson.fromJson(in, ResultObject.class);
                
                if (!"SUCCESS".equalsIgnoreCase(obj.getResult())) {
                    return;
                }
                
                storePreferences();
                
                loginTextField.setVisible(false);
                passwordTextField.setVisible(false);
                
                playButton.setTextFill(Paint.valueOf("WHITE"));
                loggedIn = true;
            }
        });
    }

    @FXML
    public void onPlayClick(final ActionEvent event) {
        if (!loggedIn) {
            return;
        }
        
        final String lang = langCombo.getValue().toLowerCase();
        final String password = passwordTextField.getText();
        final String login = loginTextField.getText();

        final File baseFile = new File(FileConfig.TERA_FOLDER+"Tera.launcher.exe");
        final File launcherFile = new File(FileConfig.TERA_FOLDER+"Tera-temp.exe");
        if (launcherFile.exists()) {
            launcherFile.delete();
        }

        try {
            FileUtils.copyFile(baseFile, launcherFile, false);
            final RandomAccessFile raf = new RandomAccessFile(launcherFile, "rw");
            raf.seek(5138868);
            String serverUrl = "http://"+LoginConfig.LOGIN_URL+"/servers."+lang;
            while (serverUrl.length() < 59) {
                serverUrl += '\0';
            }
            raf.write(serverUrl.getBytes());
            raf.close();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        
        final List<String> commands = new FastList<>();
        commands.add(launcherFile.getAbsolutePath());
        commands.add("1");
        commands.add(password);
        commands.add("0");
        commands.add("1");
        commands.add(login);
        commands.add(lang);
        
        final ProcessBuilder builder = new ProcessBuilder(commands);
        builder.directory(new File(FileConfig.TERA_FOLDER));

        try {
            builder.start();
            launcherFile.deleteOnExit();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void onCloseMenu(final ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    public void onLanguageChange(final ActionEvent event) {
        this.loadUi();
    }
    
    public TextField getLoginTextField() {
        return loginTextField;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public ComboBox<String> getLangCombo() {
        return langCombo;
    }

    public WebView getWebView() {
        return webView;
    }

    public void loadUi() {
        final Locale locale = LocaleUtils.toLocale(this.langCombo.getValue().toLowerCase());

        // TEXTS
        final I18nService i18nService = I18nService.getInstance();
        this.loginButton.setText(i18nService.translate(locale, "ui.button.login.text"));
        this.playButton.setText(i18nService.translate(locale, "ui.button.play.text"));

        // COLORS
        this.playButton.setTextFill(Paint.valueOf("BLACK"));

        // WEB
        this.webView.getEngine().load("http://"+LoginConfig.LOGIN_URL+"/welcome");
    }
    
    public void loadPreferences() {
        this.loginTextField.setText(PreferencesUtils.get("login", ""));
        this.passwordTextField.setText(PreferencesUtils.get("password", ""));
        this.langCombo.setValue(PreferencesUtils.get("lang", "EN"));
    }
    
    public void storePreferences() {
        PreferencesUtils.put("login", this.loginTextField.getText());
        PreferencesUtils.put("password", this.passwordTextField.getText());
        PreferencesUtils.put("lang", this.langCombo.getValue());
    }
}
