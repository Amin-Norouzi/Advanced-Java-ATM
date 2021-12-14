package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.util.AlertUtils;
import com.aminnorouzi.atm.helper.ForgotPassword;
import com.aminnorouzi.atm.util.PageUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {
    @FXML
    private Label loginLabel;
    @FXML
    private TextField codeField;
    @FXML
    private Label headerLabel;
    @FXML
    private Button resetButton;
    @FXML
    private Button sendButton;
    @FXML
    private HBox sendCodeSection;
    @FXML
    private TextField usernameField;
    @FXML
    private BorderPane root;

    private ForgotPassword forgotPassword;
    private final BooleanProperty isSentProperty = new SimpleBooleanProperty(false);

    @FXML
    private void handleReset(ActionEvent event) throws IOException {
        String code = codeField.getText();

        if (forgotPassword.validateCode(code)) {
            forgotPassword.resetPassword();
            AlertUtils.showSuccessDialog( "Operation succeed", "You have successfully changed your password.\n"
                    + forgotPassword.getPasswordInfo());
            switchToLoginPage(event);
        } else {
            AlertUtils.showErrorDialog("Operation failed", "The recovery code is incorrect!");
        }
    }

    private void switchToLoginPage(ActionEvent event) throws IOException {
        PageUtils.switchToLoginView(event);
    }

    @FXML
    private void handleSend(ActionEvent event) {
        String username = usernameField.getText().toLowerCase();

        if (forgotPassword.validateUsername(username)) {
            forgotPassword.loadUserAccount(username);
            forgotPassword.sendCode();
            isSentProperty.set(true);
            AlertUtils.showSuccessDialog("Operation succeed", "The recovery code was sent to your email address.");
        } else {
            AlertUtils.showErrorDialog("Operation failed", "The username dose not belong to any user.");
        }
    }

    @FXML
    private void handleBackToLoginPage(MouseEvent event) throws IOException {
        PageUtils.switchToLoginView(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        forgotPassword = new ForgotPassword();
        AlertUtils.setRoot(root);

        sendButton.disableProperty().bind(usernameField.textProperty().length().lessThan(5)
                .or(usernameField.textProperty().length().greaterThan(16)));

        codeField.disableProperty().bind(isSentProperty.not());

        resetButton.disableProperty().bind(isSentProperty.not()
                .or(codeField.textProperty().isEmpty()));

        sendCodeSection.disableProperty().bind(isSentProperty);
    }
}
