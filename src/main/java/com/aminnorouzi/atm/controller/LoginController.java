package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.util.AlertUtils;
import com.aminnorouzi.atm.helper.Login;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PageUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label forgotPasswordLabel;
    @FXML
    private Label headerLabel;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label signUpLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private BorderPane root;

    private Login login;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText().toLowerCase();
        String password = passwordField.getText();

        if (login.validateLogin(username, password)) {
            AlertUtils.showSuccessDialog(login.getUserInfo(), "You are all done! Now you are signed in your account!");
            switchToAtmHome(event, login.getUser());
        } else {
            AlertUtils.showErrorDialog("Operation failed", "The username or password is incorrect!");
        }
    }

    private void switchToAtmHome(ActionEvent event, User user) throws IOException {
        PageUtils.switchToAtmHomeView(event, user);
    }

    @FXML
    private void handleForgotPassword(MouseEvent event) throws IOException {
        PageUtils.switchToSignUpView(event);
    }

    @FXML
    private void handleSignUp(MouseEvent event) throws IOException {
        PageUtils.switchToForgotPasswordView(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login = new Login();
        AlertUtils.setRoot(root);

        loginButton.disableProperty().bind(usernameField.textProperty().isEmpty()
                .or(usernameField.textProperty().length().lessThan(5))
                .or(passwordField.textProperty().isEmpty())
                .or(passwordField.textProperty().length().lessThan(4)));
    }
}
