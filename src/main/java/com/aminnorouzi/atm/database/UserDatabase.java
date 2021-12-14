package com.aminnorouzi.atm.database;

import com.aminnorouzi.atm.model.Card;
import com.aminnorouzi.atm.model.Transaction;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.QueryUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private final Connection connection;
    private final CardDatabase cardDatabase;
    private final TransactionDatabase transactionDatabase;

    private static final String TABLE = "users";
    private static final String ALL = "*";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FULL_NAME = "fullName";
    private static final String EMAIL = "email";

    public UserDatabase(Connection connection) {
        this.connection = connection;
        this.cardDatabase = new CardDatabase(this.connection);
        this.transactionDatabase = new TransactionDatabase(this.connection);
    }

    public boolean insertUser(User user) throws SQLException {
        String query = QueryUtils.insetQuery(TABLE, USERNAME + ", " + PASSWORD + ", " + FULL_NAME + ", " + EMAIL
                , "?, ?, ?, ?");

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getEmail());
            cardDatabase.insertCard(user.getCard());

            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            return false;
        }
    }

    public boolean updateUser(User user) throws SQLException {
        String query = QueryUtils.updateQuery(TABLE, (PASSWORD + " = ?, " + FULL_NAME + " = ?, " + EMAIL + " = ?"), (USERNAME + " = ?"));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(String username) throws SQLException {
        String query = QueryUtils.deleteQuery(TABLE, (USERNAME + " = ?"));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            cardDatabase.deleteCardByHolder(username);
            transactionDatabase.deleteTransactionsByDoer(username);

            return statement.executeUpdate() > 0;
        }
    }

    public User getUser(String username) throws SQLException {
        String query = QueryUtils.selectQuery((USERNAME + ", " + PASSWORD + ", " + FULL_NAME + ", " + EMAIL), TABLE
                , (USERNAME + " = ?"));

        User user = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user = getData(resultSet);
            }
        }
        return user;
    }

    public List<User> getAllUsers() throws SQLException {
        String query = QueryUtils.selectQuery(ALL, TABLE);

        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                getData(users, resultSet);
            }
        }
        return users;
    }

    private User getData(ResultSet resultSet) throws SQLException {
        String username = resultSet.getString(USERNAME);
        String password = resultSet.getString(PASSWORD);
        String fullName = resultSet.getString(FULL_NAME);
        String email = resultSet.getString(EMAIL);
        Card card = cardDatabase.getCardByHolder(username);
        List<Transaction> transactions = transactionDatabase.getTransactionsByDoer(username);

        return new User(username, password, fullName, email, card, transactions);
    }

    private void getData(List<User> users, ResultSet resultSet) throws SQLException {
        String username = resultSet.getString(USERNAME);
        String password = resultSet.getString(PASSWORD);
        String fullName = resultSet.getString(FULL_NAME);
        String email = resultSet.getString(EMAIL);
        Card card = cardDatabase.getCardByHolder(username);
        List<Transaction> transactions = transactionDatabase.getTransactionsByDoer(username);

        users.add(new User(username, password, fullName, email, card, transactions));
    }
}
