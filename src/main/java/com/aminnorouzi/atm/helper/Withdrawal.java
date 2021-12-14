package com.aminnorouzi.atm.helper;

import com.aminnorouzi.atm.model.Card;
import com.aminnorouzi.atm.model.User;

import java.math.BigDecimal;

public class Withdrawal extends Payment{

    public Withdrawal(User user) {
        super(user);
    }

    public boolean executeWithdrawal(String note, BigDecimal amount) {
        Card card = getUser().getCard();
        createWithdrawal(card, card, note, amount);

        BigDecimal balance = calculateWithdrawal(card.getBalance(), amount);

        if (updateCardBalance(card, balance) && insertTransaction()) {
            updateUserTransactionsList();
            return true;
        }
        return false;
    }

    public String getWithdrawalInfo() {
        return "Tracking code: " + getTransaction().getTrackingCode();
    }
}
