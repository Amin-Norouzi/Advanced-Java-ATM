package com.aminnorouzi.atm.helper;

import com.aminnorouzi.atm.model.Card;
import com.aminnorouzi.atm.model.User;

import java.math.BigDecimal;

public class Deposit extends Payment {

    public Deposit(User user) {
        super(user);
    }

    public boolean executeDeposit(String note, BigDecimal amount) {
        Card card = getUser().getCard();
        createDeposit(card, card, note, amount);

        BigDecimal balance = calculateDeposit(card.getBalance(), amount);

        if (updateCardBalance(card, balance) && insertTransaction()) {
            updateUserTransactionsList();
            return true;
        }
        return false;
    }

    public String getDepositInfo() {
        return "Tracking code: " + getTransaction().getTrackingCode();
    }
}
