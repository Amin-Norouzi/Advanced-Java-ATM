package com.aminnorouzi.atm.database;

import com.aminnorouzi.atm.model.*;
import com.aminnorouzi.atm.util.QueryUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDatabase {
    private final Connection connection;

    private static final String TABLE = "transactions";
    private static final String ALL = "*";
    private static final String DOER = "doer";
    private static final String TRACKING_CODE = "trackingCode";
    private static final String SENDER = "sender";
    private static final String RECEIVER = "receiver";
    private static final String NOTE = "note";
    private static final String DATE_TIME = "dateTime";
    private static final String AMOUNT = "amount";
    private static final String TRANSACTION_TYPE = "transactionType";

    public TransactionDatabase(Connection connection) {
        this.connection = connection;
    }

    public boolean insertTransaction(Transaction transaction) throws SQLException {
        String query = QueryUtils.insetQuery(TABLE, (DOER + ", " + TRACKING_CODE + ", " + SENDER + ", " + RECEIVER + ", "
                + NOTE + ", " + DATE_TIME + ", " + AMOUNT + ", " + TRANSACTION_TYPE), "?, ?, ?, ?, ?, ?, ?, ?");

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, transaction.getDoer());
            statement.setString(2, transaction.getTrackingCode());
            statement.setString(3, transaction.getSender());
            statement.setString(4, transaction.getReceiver());
            statement.setString(5, transaction.getNote());
            statement.setString(6, transaction.getDateTime());
            statement.setBigDecimal(7, transaction.getAmount());
            statement.setString(8, transaction.getTransactionType());

            return statement.executeUpdate() > 0;
        }
    }

    public void deleteTransactionsByDoer(String doer) throws SQLException {
        String query = QueryUtils.deleteQuery(TABLE, (DOER + " = ?"));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, doer);

            statement.executeUpdate();
        }
    }

    public List<Transaction> getTransactionsByDoer(String doer) throws SQLException {
        String query = QueryUtils.selectQuery((DOER + ", " + TRACKING_CODE + ", " + SENDER + ", " + RECEIVER + ", "
                + NOTE + ", " + DATE_TIME + ", " + AMOUNT + ", " + TRANSACTION_TYPE), TABLE, (DOER + " = ?"));

        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, doer);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                getData(transactions, resultSet);
            }
        }
        return transactions;
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        String query = QueryUtils.selectQuery(ALL, TABLE);
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                getData(transactions, resultSet);
            }
        }
        return transactions;
    }

    private void getData(List<Transaction> transactions, ResultSet resultSet) throws SQLException {
        String doer = resultSet.getString(DOER);
        String trackingCode = resultSet.getString(TRACKING_CODE);
        String sender = resultSet.getString(SENDER);
        String receiver = resultSet.getString(RECEIVER);
        String note = resultSet.getString(NOTE);
        String dateTime = resultSet.getString(DATE_TIME);
        BigDecimal amount = resultSet.getBigDecimal(AMOUNT);
        String transactionType = resultSet.getString(TRANSACTION_TYPE);

        transactions.add(new Transaction(doer, trackingCode, sender, receiver, note, dateTime, amount, transactionType));
    }
}
