package com.aminnorouzi.atm.database;

import com.aminnorouzi.atm.model.Card;
import com.aminnorouzi.atm.util.QueryUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDatabase {
    private final Connection connection;

    private static final String TABLE = "cards";
    private static final String ALL = "*";
    private static final String HOLDER = "holder";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String PASSWORD = "password";
    private static final String BALANCE = "balance";

    public CardDatabase(Connection connection) {
        this.connection = connection;
    }

    public void insertCard(Card card) throws SQLException {
        String query = QueryUtils.insetQuery(TABLE, (HOLDER + ", " + CARD_NUMBER + ", " + PASSWORD + ", " + BALANCE)
                , "?, ?, ?, ?");

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, card.getHolder());
            statement.setString(2, card.getCardNumber());
            statement.setString(3, card.getPassword());
            statement.setBigDecimal(4, card.getBalance());

            statement.executeUpdate();
        }
    }

    public boolean updateCard(Card card) throws SQLException {
        String query = QueryUtils.updateQuery(TABLE, (PASSWORD + " = ?, " + BALANCE + " = ?"), (CARD_NUMBER + " = ?"));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, card.getPassword());
            statement.setBigDecimal(2, card.getBalance());
            statement.setString(3, card.getCardNumber());

            return statement.executeUpdate() > 0;
        }
    }

    public void deleteCardByHolder(String holder) throws SQLException {
        String query = QueryUtils.deleteQuery(TABLE, (HOLDER + " = ?"));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, holder);

            statement.executeUpdate();
        }
    }

    public Card getCardByHolder(String holder) throws SQLException {
        String query = QueryUtils.selectQuery((HOLDER + ", " + CARD_NUMBER + ", " + PASSWORD + ", " + BALANCE)
                , TABLE, (HOLDER + " = ?"));

        Card card = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, holder);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                card = getData(resultSet);
            }
        }
        return card;
    }

    public Card getCardByCardNumber(String cardNumber) throws SQLException {
        String query = QueryUtils.selectQuery((HOLDER + ", " + CARD_NUMBER + ", " + PASSWORD + ", " + BALANCE), TABLE
                , (CARD_NUMBER + " = ?"));

        Card card = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cardNumber);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                card = getData(resultSet);
            }
        }
        return card;
    }

    public List<Card> getAllCards() throws SQLException {
        String query = QueryUtils.selectQuery(ALL, TABLE);

        List<Card> cards = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                getData(cards, resultSet);
            }
        }
        return cards;
    }

    private Card getData(ResultSet resultSet) throws SQLException {
        String holder = resultSet.getString(HOLDER);
        String cardNumber = resultSet.getString(CARD_NUMBER);
        String password = resultSet.getString(PASSWORD);
        BigDecimal balance = resultSet.getBigDecimal(BALANCE);

        return new Card(holder, cardNumber, password, balance);
    }

    private void getData(List<Card> cards, ResultSet resultSet) throws SQLException {
        String holder = resultSet.getString(HOLDER);
        String cardNumber = resultSet.getString(CARD_NUMBER);
        String password = resultSet.getString(PASSWORD);
        BigDecimal balance = resultSet.getBigDecimal(BALANCE);

        cards.add(new Card(holder, cardNumber, password, balance));
    }
}
