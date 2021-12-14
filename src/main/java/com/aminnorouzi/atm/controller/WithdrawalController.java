package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.util.AlertUtils;
import com.aminnorouzi.atm.helper.Withdrawal;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PageUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class WithdrawalController implements Initializable {
    @FXML
    private TextField amountField;
    @FXML
    private Button cancelButton;
    @FXML
    private Label headerLabel;
    @FXML
    private TextArea noteField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button payButton;
    @FXML
    private TextField senderCardNumberField;
    @FXML
    private BorderPane root;

    private final User user;
    private Withdrawal withdrawal;
    private final BooleanProperty isValidAmountProperty = new SimpleBooleanProperty(false);

    @FXML
    private void handlePay(ActionEvent event) throws IOException {
        String password = passwordField.getText();
        BigDecimal amount = new BigDecimal(amountField.getText());
        String note = noteField.getText();

        if (withdrawal.validateCardPassword(password)) {
            withdrawal.executeWithdrawal(note, amount);
            AlertUtils.showSuccessDialog("Operation succeed", "The withdrawal was successful.\n"
                    + withdrawal.getWithdrawalInfo());
            switchToAtmHome(event, withdrawal.getUser());
        } else {
            AlertUtils.showErrorDialog("Operation failed", "The card password is incorrect!");
        }
    }

    private void switchToAtmHome(ActionEvent event, User user) throws IOException {
        PageUtils.switchToAtmHomeView(event, user);
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        switchToAtmHome(event, user);
    }

    private void validateAmountPattern(String text) {
        isValidAmountProperty.set(withdrawal.validateAmountPattern(text));

        if (withdrawal.validateAmountPattern(text)) {
            amountField.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.8);");
        } else {
            amountField.setStyle("-fx-border-color: rgba(255, 69, 58, 0.5); -fx-text-fill: #FF453A;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        withdrawal = new Withdrawal(user);
        AlertUtils.setRoot(root);

        senderCardNumberField.setText(user.getCard().getCardNumber());

        payButton.disableProperty().bind(amountField.textProperty().isEmpty()
                .or(passwordField.textProperty().isEmpty())
                .or(passwordField.textProperty().length().lessThan(4))
                .or(passwordField.textProperty().length().greaterThan(8))
                .or(isValidAmountProperty.not()));

        amountField.textProperty().addListener((v, oldValue, newValue) -> validateAmountPattern(newValue));
    }

    public WithdrawalController(User user) {
        this.user = user;
    }
}
