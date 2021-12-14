package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.util.PageUtils;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class SplashController implements Initializable {
    @FXML
    private Label errorLabel;
    @FXML
    private Label headerLabel;
    @FXML
    private ProgressBar loadingBar;
    @FXML
    private ImageView loadingImage;
    @FXML
    private BorderPane root;
    @FXML
    private Label subLabel;
    @FXML
    private Button tryButton;
    @FXML
    private Label versionLabel;

    @FXML
    private void handleTryAgain(ActionEvent event) {
        loadingImage.setVisible(true);
        tryButton.setVisible(false);
        errorLabel.setVisible(false);

        loading();
    }

    private boolean netIsAvailable() {
        try {
            final URL url = new URL("https://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private int getRandom() {
        return ((int) (Math.random() * 7));
    }

    private void loading() {
        int random = getRandom();

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), loadingImage);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(random + 5);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.play();

        rotateTransition.setOnFinished(e -> {
            if (netIsAvailable()) {
                loadingImage.setVisible(false);
                loadingBar.setVisible(true);

                loadProgram();
            } else {
                loadingImage.setVisible(false);
                tryButton.setVisible(true);
                errorLabel.setVisible(true);
            }
        });
    }

    private void loadProgram() {
        new Thread(() -> {
            double progress = 0;
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {}

                progress += 0.1;
                loadingBar.setProgress(progress);
            }

            Platform.runLater(() -> {
                try {
                    PageUtils.switchToLoginView(root);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadingImage.setImage(new Image("/images/Loading-Circle.png"));
        loading();
    }
}
