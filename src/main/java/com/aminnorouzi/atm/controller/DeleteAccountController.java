package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.util.AlertUtils;
import com.aminnorouzi.atm.helper.Settings;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PageUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAccountController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label headerLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox termsCheckBox;
    @FXML
    private TextField usernameField;
    @FXML
    private BorderPane root;

    private final User user;
    private Settings settings;

    @FXML
    private void handleDelete(ActionEvent event) throws IOException {
        String password = passwordField.getText();

        if (settings.validateUserPassword(password)) {
            settings.deleteUser();
            AlertUtils.showSuccessDialog("Operation succeed","The account has successfully been deleted.");
            switchToLogin(event);
        } else {
            AlertUtils.showErrorDialog("Operation failed", "The account password is incorrect!");
        }
    }

    private void switchToLogin(ActionEvent event) throws IOException {
        PageUtils.switchToLoginView(event);
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        PageUtils.switchToSettingsView(event, user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings = new Settings(user);
        AlertUtils.setRoot(root);

        deleteButton.disableProperty().bind(passwordField.textProperty().isEmpty()
                .or(passwordField.textProperty().length().lessThan(4))
                .or(termsCheckBox.selectedProperty().not()));

        usernameField.setText(user.getUsername());
    }

    public DeleteAccountController(User user) {
        this.user = user;
    }
}
