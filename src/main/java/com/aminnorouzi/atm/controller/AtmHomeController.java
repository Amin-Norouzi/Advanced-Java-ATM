package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PageUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AtmHomeController implements Initializable {
    @FXML
    private Button balanceButton;
    @FXML
    private Button depositButton;
    @FXML
    private Label headerLabel;
    @FXML
    private Button historyButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button transferButton;
    @FXML
    private Button withdrawalButton;
    @FXML
    private Label aboutLabel;
    @FXML
    private BorderPane root;

    private Stage stage;
    private final User user;
    private boolean isAlive = true;

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        isAlive = false;
        PageUtils.switchToLoginView(event);
    }

    @FXML
    private void handleDeposit(ActionEvent event) throws IOException {
        isAlive = false;
        PageUtils.switchToDepositView(event, user);
    }

    @FXML
    private void handleWithdrawal(ActionEvent event) throws IOException {
        isAlive = false;
        PageUtils.switchToWithdrawalView(event, user);
    }

    @FXML
    private void handleTransfer(ActionEvent event) throws IOException {
        isAlive = false;
        PageUtils.switchToTransferView(event, user);
    }

    @FXML
    private void handleBalance(ActionEvent event) throws IOException {
        isAlive = false;
        PageUtils.switchToBalanceView(event, user);
    }

    @FXML
    private void handleHistory(ActionEvent event) throws IOException {
        isAlive = false;
        PageUtils.switchToHistoryView(event, user);
    }

    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        isAlive = false;
        PageUtils.switchToSettingsView(event, user);
    }

    @FXML
    private void handleAbout(MouseEvent event) throws IOException {
        PageUtils.switchToAboutView(root);
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("EE, HH:mm").format(Calendar.getInstance().getTime());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        headerLabel.setText("Home (" + getCurrentTime().toUpperCase() + ")");

        new Thread(() -> {
            String currentTime;
            while (isAlive) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}

                currentTime = getCurrentTime();
                String finalCurrentTime = currentTime.toUpperCase();
                Platform.runLater(() -> headerLabel.setText("Home (" + finalCurrentTime + ")"));
            }
        }).start();

        stage.setOnCloseRequest(e -> isAlive = false);
    }

    public AtmHomeController(User user) {
        this.user = user;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
