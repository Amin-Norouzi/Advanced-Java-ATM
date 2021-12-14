package com.aminnorouzi.atm.helper;

import com.aminnorouzi.atm.model.Card;
import com.aminnorouzi.atm.model.Transaction;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.TextUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SignUp extends Account {

    // USER SECTION

    public void generateUser(User user) {
        String username = user.getUsername();
        String password = encodePassword(user.getPassword(), username);
        String fullName = user.getFullName();
        String email = user.getEmail();

        Card card = generateCard(username);
        List<Transaction> transactions = new ArrayList<>();

        setUser(new User(username, password, fullName, email, card, transactions));
        insertUser();
    }

    private boolean insertUser() {
        try {
            return getUserDatabase().insertUser(getUser());
        } catch (SQLException exception) {
            return false;
        }
    }

    // CARD SECTION

    public String getCardInfo() {
        Card card = getUser().getCard();
        String thePlainPassword = decodePassword(card.getPassword(), card.getCardNumber());

        return "Card holder: " + card.getHolder() + "\nCard number: " + card.getCardNumber() + "\nCard password: " + thePlainPassword
                + "\nCard balance: " + card.getBalance();
    }

    private Card generateCard(String holder) {
        String cardNumber = generateCardNumber();
        String cardPassword = encodePassword(generatePassword(), cardNumber);

        return new Card(holder, cardNumber, cardPassword, new BigDecimal(0));
    }

    private String generateCardNumber() {
        String cardNumber;
        do {
            cardNumber = "811206" + TextUtils.random(10, false, true);
        } while (!isNewCard(cardNumber));

        return cardNumber;
    }

    private boolean isNewCard(String cardNumber) {
        try {
            List<Card> cards = getCardDatabase().getAllCards();
            for (Card card : cards) {
                if (card.getCardNumber().equals(cardNumber)) {
                    return false;
                }
            }
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }
}