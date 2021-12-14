package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PageUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.aminnorouzi.atm.util.TextUtils.toCurrency;

public class BalanceController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label headerLabel;

    private final User user;

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        PageUtils.switchToAtmHomeView(event, user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        balanceLabel.setText(toCurrency(user.getCard().getBalance().toString()));
    }

    public BalanceController(User user) {
        this.user = user;
    }
}
