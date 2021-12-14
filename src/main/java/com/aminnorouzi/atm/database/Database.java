package com.aminnorouzi.atm.database;

import com.aminnorouzi.atm.util.QueryUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private final String connectionString;
    private Connection connection;

    private static final String TEXT = " TEXT, ";
    private static final String INTEGER = " INTEGER, ";
    private static final String PRIMARY_KEY = "PRIMARY KEY";

    public Database(String connectionString) {
        this.connectionString = connectionString;
    }

    public boolean open() {
        try {
            connection = DriverManager.getConnection(connectionString);

            initUserTable();
            initCardTable();
            initTransactionTable();

            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    public boolean close() {
        try {
            connection.close();
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void initUserTable() throws SQLException {
        String query = QueryUtils.creatTableQuery("users", ("username" + TEXT + "password" + TEXT + "fullName" + TEXT
                + "email" + TEXT + PRIMARY_KEY + "(username)"));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    private void initCardTable() throws SQLException {
        String query = QueryUtils.creatTableQuery("cards", ("holder" + TEXT + "cardNumber" + TEXT + "password" + TEXT
                + "balance" + INTEGER + PRIMARY_KEY + "(cardNumber)"));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    private void initTransactionTable() throws SQLException {
        String query = QueryUtils.creatTableQuery("transactions", ("doer" + TEXT + "trackingCode" + TEXT + "sender" + TEXT
                + "receiver" + TEXT + "note" + TEXT + "dateTime" + TEXT + "amount" + INTEGER + "transactionType" + TEXT + PRIMARY_KEY
                + "(trackingCode)"));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }
}