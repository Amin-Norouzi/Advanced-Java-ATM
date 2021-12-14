package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.util.AlertUtils;
import com.aminnorouzi.atm.helper.SignUp;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PageUtils;
import com.aminnorouzi.atm.util.TextUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private TextField emailField;
    @FXML
    private TextField fullNameField;
    @FXML
    private Label headerLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField usernameField;
    @FXML
    private BorderPane root;

    private final BooleanProperty isValidProperty = new SimpleBooleanProperty(false);
    private SignUp signUp;

    @FXML
    private void handleSignUp(ActionEvent event) throws IOException {
        String username = usernameField.getText().toLowerCase();
        String password = TextUtils.normalize(passwordField.getText());
        String fullName = TextUtils.normalize(fullNameField.getText().toLowerCase());
        String email = emailField.getText().toLowerCase();
        User user = new User(username, password, fullName, email);

        if (signUp.isNewUser(username)) {
            if (signUp.isNewEmail(email)) {
                signUp.generateUser(user);
                AlertUtils.showSuccessDialog("Operation succeed", signUp.getCardInfo());
                switchToAtmHome(event, signUp.getUser());
            } else {
                AlertUtils.showErrorDialog("Operation failed", "This email is already occupied!");
            }
        } else {
            AlertUtils.showErrorDialog("Operation failed", "This username is already occupied!");
        }
    }

    private void switchToAtmHome(ActionEvent event, User user) throws IOException {
        PageUtils.switchToAtmHomeView(event, user);
    }

    @FXML
    private void handleLogin(MouseEvent event) throws IOException {
        PageUtils.switchToLoginView(event);
    }

    private void validateUsernamePattern(String username) {
        isValidProperty.set(signUp.validateUsernamePattern(username));

        if (this.signUp.validateUsernamePattern(username)) {
            usernameField.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.8);");
        } else {
            usernameField.setStyle("-fx-border-color: rgba(255, 69, 58, 0.5); -fx-text-fill: #FF453A;");
        }
    }

    private void validateEmailPattern(String email) {
        isValidProperty.set(signUp.validateEmailPattern(email));

        if (signUp.validateEmailPattern(email)) {
            emailField.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.8);");
        } else {
            emailField.setStyle("-fx-border-color: rgba(255 , 69, 58, 0.5); -fx-text-fill: #FF453A;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUp = new SignUp();
        AlertUtils.setRoot(root);

        signUpButton.disableProperty().bind(fullNameField.textProperty().isEmpty()
                .or(usernameField.textProperty().isEmpty())
                .or(emailField.textProperty().isEmpty())
                .or(passwordField.textProperty().isEmpty())
                .or(usernameField.textProperty().length().lessThan(5))
                .or(usernameField.textProperty().length().greaterThan(16))
                .or(passwordField.textProperty().length().lessThan(4))
                .or(passwordField.textProperty().length().greaterThan(32))
                .or(isValidProperty.not()));

        usernameField.textProperty().addListener((v, oldValue, newValue) -> validateUsernamePattern(newValue));
        emailField.textProperty().addListener((v, oldValue, newValue) -> validateEmailPattern(newValue));
    }
}
