package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.util.AlertUtils;
import com.aminnorouzi.atm.helper.Transfer;
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

public class TransferController implements Initializable {
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
    private TextField receiverCardNumberField;
    @FXML
    private TextField senderCardNumberField;
    @FXML
    private BorderPane root;

    private final User user;
    private Transfer transfer;
    private final BooleanProperty isValidProperty = new SimpleBooleanProperty(false);

    @FXML
    private void handlePay(ActionEvent event) throws IOException {
        String receiver = receiverCardNumberField.getText();
        String password = passwordField.getText();
        String note = noteField.getText();
        BigDecimal amount = new BigDecimal(amountField.getText());

        if (transfer.validateCardNumber(receiver)) {
            if (transfer.validateCardPassword(password)) {
                transfer.executeTransfer(receiver, note, amount);
                AlertUtils.showSuccessDialog("Operation succeed", "The transfer was successful.\n"
                        + transfer.getTransferInfo());
                switchToAtmHome(event, transfer.getUser());
            } else {
                AlertUtils.showErrorDialog("Operation failed", "The current password is incorrect!");
            }
        } else {
            AlertUtils.showErrorDialog("Operation failed", "The receiver card number is incorrect!");
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
        isValidProperty.set(transfer.validateAmountPattern(text));

        if (transfer.validateAmountPattern(text)) {
            amountField.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.8);");
        } else {
            amountField.setStyle("-fx-border-color: rgba(255,69,58,0.5); -fx-text-fill: #FF453A;");
        }
    }

    private void validateReceiverCardNumberPattern(String text) {
        isValidProperty.set(transfer.validateCardNumberPattern(text) && !user.getCard().getCardNumber().equals(text));

        if (transfer.validateCardNumberPattern(text)) {
            receiverCardNumberField.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.8);");
        } else {
            receiverCardNumberField.setStyle("-fx-border-color: rgba(255, 69, 58, 0.5); -fx-text-fill: #FF453A;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transfer = new Transfer(user);
        AlertUtils.setRoot(root);

        senderCardNumberField.setText(user.getCard().getCardNumber());

        payButton.disableProperty().bind(receiverCardNumberField.textProperty().isEmpty()
                .or(amountField.textProperty().isEmpty())
                .or(passwordField.textProperty().isEmpty())
                .or(receiverCardNumberField.textProperty().length().isNotEqualTo(16))
                .or(passwordField.textProperty().length().lessThan(4))
                .or(passwordField.textProperty().length().greaterThan(8))
                .or(isValidProperty.not()));

        amountField.textProperty().addListener((v, oldValue, newValue) -> validateAmountPattern(newValue));
        receiverCardNumberField.textProperty().addListener((v, oldValue, newValue) -> validateReceiverCardNumberPattern(newValue));
    }

    public TransferController(User user) {
        this.user = user;
    }
}
