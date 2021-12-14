package com.aminnorouzi.atm.helper;

import com.aminnorouzi.atm.Main;
import com.aminnorouzi.atm.database.CardDatabase;
import com.aminnorouzi.atm.model.Card;
import com.aminnorouzi.atm.model.User;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Transfer extends Payment {

    public Transfer(User user) {
        super(user);
    }

    public boolean executeTransfer(String cardNumber, String note, BigDecimal amount) {
        if(executeDeposit(cardNumber, note, amount)) {
            return executeWithdrawal(cardNumber, note, amount);
        }
        return false;
    }

    public boolean executeWithdrawal(String cardNumber, String note, BigDecimal amount) {
        Card senderCard = getUser().getCard();
        Card receiverCard = getCardByCardNumber(cardNumber);
        createWithdrawal(senderCard, receiverCard, note, amount);

        BigDecimal balance = calculateWithdrawal(senderCard.getBalance(), amount);

        if (updateCardBalance(senderCard, balance) && insertTransaction()) {
            updateUserTransactionsList();
            return true;
        }
        return false;
    }

    public boolean executeDeposit(String cardNumber, String note, BigDecimal amount) {
        Card senderCard = getUser().getCard();
        Card receiverCard = getCardByCardNumber(cardNumber);
        createDeposit(senderCard, receiverCard, note, amount);

        BigDecimal balance = calculateDeposit(receiverCard.getBalance(), amount);

        return updateCardBalance(receiverCard, balance) && insertTransaction();
    }

    private Card getCardByCardNumber(String cardNumber) {
        try {
            CardDatabase cardDatabase = new CardDatabase(Main.getConnection());
            return cardDatabase.getCardByCardNumber(cardNumber);
        } catch (SQLException exception) {
            return null;
        }
    }

    public String getTransferInfo() {
        return "Tracking code: " + getTransaction().getTrackingCode();
    }
}
