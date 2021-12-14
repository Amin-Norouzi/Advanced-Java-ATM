package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PageUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.aminnorouzi.atm.util.TextUtils.capitalize;


public class SettingsController implements Initializable {
    @FXML
    private Button accountInfoArrow;
    @FXML
    private AnchorPane accountInfoButton;
    @FXML
    private Label accountInfoHeader;
    @FXML
    private Button accountInfoIcon;
    @FXML
    private Label accountInfoText;
    @FXML
    private Button backButton;
    @FXML
    private Label cardNumberLabel;
    @FXML
    private Button cardPasswordArrow;
    @FXML
    private AnchorPane cardPasswordButton;
    @FXML
    private Label cardPasswordHeader;
    @FXML
    private Button cardPasswordIcon;
    @FXML
    private Label cardPasswordText;
    @FXML
    private Button deleteAccountArrow;
    @FXML
    private Label deleteAccountHeader;
    @FXML
    private Button deleteAccountIcon;
    @FXML
    private Label deleteAccountText;
    @FXML
    private Label emailLabel;
    @FXML
    private Label fullNameLabel;
    @FXML
    private AnchorPane deleteAccountButton;
    @FXML
    private Label headerLabel;
    @FXML
    private Button userPasswordArrow;
    @FXML
    private AnchorPane userPasswordButton;
    @FXML
    private Label userPasswordHeader;
    @FXML
    private Button userPasswordIcon;
    @FXML
    private Label userPasswordText;
    @FXML
    private Label usernameLabel;

    private final User user;

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        PageUtils.switchToAtmHomeView(event, user);
    }

    @FXML
    private void handleAccountInfo(MouseEvent event) throws IOException {
        PageUtils.switchToAccountInfoView(event, user);
    }

    @FXML
    private void handleUserPassword(MouseEvent event) throws IOException {
        PageUtils.switchToUserPasswordView(event, user);
    }

    @FXML
    private void handleCardPassword(MouseEvent event) throws IOException {
        PageUtils.switchToCardPasswordView(event, user);
    }

    @FXML
    private void handleDeleteAccount(MouseEvent event) throws IOException {
        PageUtils.switchToDeleteAccountView(event, user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullNameLabel.setText(capitalize(user.getFullName()));
        usernameLabel.setText("@" + user.getUsername());
        emailLabel.setText(user.getEmail());
        cardNumberLabel.setText(user.getCard().getCardNumber());
    }

    public SettingsController(User user) {
        this.user = user;
    }
}
