package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.util.AlertUtils;
import com.aminnorouzi.atm.helper.Settings;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PageUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardPasswordController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private Label headerLabel;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private Button saveButton;
    @FXML
    private BorderPane root;

    private final User user;
    private Settings settings;

    @FXML
    private void handleSave(ActionEvent event) throws IOException {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();

        if (settings.validateCardPassword(currentPassword)) {
            settings.changeCardPassword(newPassword);
            AlertUtils.showSuccessDialog("Operation succeed","The password has successfully been updated.");
            switchToSettings(event, settings.getUser());
        } else {
            AlertUtils.showErrorDialog("Operation failed", "The current password is incorrect!");
        }
    }

    private void switchToSettings(ActionEvent event, User user) throws IOException {
        PageUtils.switchToSettingsView(event, user);
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        switchToSettings(event, user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings = new Settings(user);
        AlertUtils.setRoot(root);

        saveButton.disableProperty().bind(currentPasswordField.textProperty().isEmpty()
                .or(newPasswordField.textProperty().isEmpty())
                .or(confirmPasswordField.textProperty().isEmpty())
                .or(currentPasswordField.textProperty().length().lessThan(4))
                .or(newPasswordField.textProperty().length().lessThan(4))
                .or(confirmPasswordField.textProperty().length().lessThan(4))
                .or(currentPasswordField.textProperty().length().greaterThan(8))
                .or(newPasswordField.textProperty().length().greaterThan(8))
                .or(confirmPasswordField.textProperty().length().greaterThan(8))
                .or(newPasswordField.textProperty().isNotEqualTo(confirmPasswordField.textProperty()))
                .or(currentPasswordField.textProperty().isEqualTo(newPasswordField.textProperty())));
    }

    public CardPasswordController(User user) {
        this.user = user;
    }
}
