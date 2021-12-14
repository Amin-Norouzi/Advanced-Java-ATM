package com.aminnorouzi.atm.controller;

import com.aminnorouzi.atm.util.AlertUtils;
import com.aminnorouzi.atm.helper.History;
import com.aminnorouzi.atm.model.Transaction;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PageUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import static com.aminnorouzi.atm.util.TextUtils.hide;
import static com.aminnorouzi.atm.util.TextUtils.toCurrency;

public class HistoryController implements Initializable {
    @FXML
    private TableView<Transaction> transactionsTable;
    @FXML
    private TableColumn<Transaction, String> transactionTypeColumn;
    @FXML
    private TableColumn<Transaction, String> trackingCodeColumn;
    @FXML
    private TableColumn<Transaction, BigDecimal> amountColumn;
    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private Button backButton;
    @FXML
    private Label headerLabel;
    @FXML
    private BorderPane root;

    private final User user;
    private History history;
    private final ObservableList<Transaction> data = FXCollections.observableArrayList();

    @FXML
    private void handleClick(MouseEvent event) {
        Transaction transaction = transactionsTable.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && transaction != null) {

            String transactionDetails = setTransactionDetails(transaction);
            int closeCode = showTransactionDetails(transactionDetails);

            if (closeCode == 1) {
                saveTransaction(getCurrentStage(), transaction.getTrackingCode(), transactionDetails);
            }
        }

        transactionsTable.getSelectionModel().clearSelection();
    }

    private void saveTransaction(Stage stage, String trackingCode, String details) {
        if (history.saveTransaction(stage, trackingCode, details)) {
            AlertUtils.showSuccessDialog("Operation succeed", "The transaction successfully saved.");
        } else {
            AlertUtils.showWarningDialog("Operation failed", "No directory is selected!");
        }
    }

    private int showTransactionDetails(String text) {
        return AlertUtils.showInformationDialog("Transaction receipt", text);
    }

    private String setTransactionDetails(Transaction transaction) {
        StringBuilder details = new StringBuilder("\n\nTracking code: " + transaction.getTrackingCode()
                + "\nAmount: " + toCurrency(transaction.getAmount().toString()) + "\nDate: " + transaction.getDateTime()
                + "\nNote: " + transaction.getNote());

        String mark = "********";

        if (transaction.getSender().equals(transaction.getReceiver())) {
            switch (transaction.getTransactionType()) {
                case "DEPOSIT" -> {
                    return details.insert(0, "To: " + hide(4, 12, mark, transaction.getReceiver())).toString();
                }
                case "WITHDRAWAL" -> {
                    return details.insert(0, "From: " + hide(4, 12, mark, transaction.getSender())).toString();
                }
            }
        }

        return details.insert(0, "To: " + hide(4, 12, mark, transaction.getReceiver())
                + "\nFrom: " + hide(4, 12, mark, transaction.getSender())).toString();
    }

    private Stage getCurrentStage() {
        return (Stage) root.getScene().getWindow();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        PageUtils.switchToAtmHomeView(event, user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        history = new History(user);
        AlertUtils.setRoot(root);

        data.addAll(user.getTransactions());

        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        trackingCodeColumn.setCellValueFactory(new PropertyValueFactory<>("trackingCode"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        transactionsTable.setItems(data);
        transactionsTable.getSortOrder().add(dateColumn);
    }

    public HistoryController(User user) {
        this.user = user;
    }
}
