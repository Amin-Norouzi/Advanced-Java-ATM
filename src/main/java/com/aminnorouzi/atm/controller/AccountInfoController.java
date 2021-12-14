package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.util.AlertUtils;
import com.aminnorouzi.atm.helper.Settings;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PageUtils;
import com.aminnorouzi.atm.util.TextUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.aminnorouzi.atm.util.TextUtils.capitalize;

public class AccountInfoController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private TextField emailField;
    @FXML
    private TextField fullNameField;
    @FXML
    private Label headerLabel;
    @FXML
    private Button saveButton;
    @FXML
    private TextField usernameField;
    @FXML
    private BorderPane root;

    private final User user;
    private Settings settings;
    private final BooleanProperty modifiedFullNameProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty modifiedEmailProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty isValidEmailProperty = new SimpleBooleanProperty(true);

    @FXML
    private void handleSave(ActionEvent event) throws IOException {
        String fullName = TextUtils.normalize(fullNameField.getText().toLowerCase());
        String email = TextUtils.normalize(emailField.getText().toLowerCase());

        if (modifiedEmailProperty.get()) {
            if (settings.isNewEmail(email)) {
                initUserInfo(fullName, email, "both", event);
            } else {
                AlertUtils.showErrorDialog("Operation failed", "This email is already occupied!");
            }
        } else {
            initUserInfo(fullName, email, "fullName", event);
        }
    }

    private void initUserInfo(String fullName, String email, String which, ActionEvent event) throws IOException {
        settings.editUserInfo(fullName, email, which);
        AlertUtils.showSuccessDialog("Operation succeed", "The account info has successfully been updated.");
        switchToSettings(event, settings.getUser());
    }

    private void switchToSettings(ActionEvent event, User user) throws IOException {
        PageUtils.switchToSettingsView(event, user);
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        switchToSettings(event, user);
    }

    private void validateEmailPattern(String email) {
        isValidEmailProperty.set(settings.validateEmailPattern(email));

        if (settings.validateEmailPattern(email)) {
            emailField.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.8);");
        } else {
            emailField.setStyle("-fx-border-color: rgba(255, 69, 58, 0.5); -fx-text-fill: #FF453A;");
        }
    }

    private void isNewFullNameProperty(String fullName) {
        String userFullName = user.getFullName();
        String polishedFullName = TextUtils.normalize(fullName).toLowerCase();
        boolean isBlank = TextUtils.isBlack(fullName);

        modifiedFullNameProperty.set(!userFullName.equals(polishedFullName) && !isBlank);
    }

    private void isNewEmailProperty(String email) {
        modifiedEmailProperty.set(!user.getEmail().equals(email));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings = new Settings(user);
        AlertUtils.setRoot(root);

        fullNameField.textProperty().addListener((v, oldValue, newValue) -> isNewFullNameProperty(newValue));
        emailField.textProperty().addListener((v, oldValue, newValue) -> isNewEmailProperty(newValue));

        emailField.textProperty().addListener((v, oldValue, newValue) -> validateEmailPattern(newValue));

        saveButton.disableProperty().bind(fullNameField.textProperty().isEmpty()
                .or(emailField.textProperty().isEmpty())
                .or(modifiedFullNameProperty.not().and(modifiedEmailProperty.not()))
                .or(isValidEmailProperty.not()));

        usernameField.setText(user.getUsername());
        fullNameField.setText(capitalize(user.getFullName()));
        emailField.setText(user.getEmail());
    }

    public AccountInfoController(User user) {
        this.user = user;
    }
}
