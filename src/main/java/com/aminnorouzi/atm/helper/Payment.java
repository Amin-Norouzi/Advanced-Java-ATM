package com.aminnorouzi.atm.helper;

import com.aminnorouzi.atm.Main;
import com.aminnorouzi.atm.database.CardDatabase;
import com.aminnorouzi.atm.database.TransactionDatabase;
import com.aminnorouzi.atm.model.Card;
import com.aminnorouzi.atm.model.Transaction;
import com.aminnorouzi.atm.model.User;
import com.aminnorouzi.atm.util.PasswordUtils;
import com.aminnorouzi.atm.util.TextUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Payment {
    private final User user;
    private Transaction transaction;
    private final CardDatabase cardDatabase;
    private final TransactionDatabase transactionDatabase;

    public Payment(User user) {
        this.user = user;
        this.cardDatabase = new CardDatabase(Main.getConnection());
        this.transactionDatabase = new TransactionDatabase(Main.getConnection());
    }

    // CARD SECTION

    public boolean updateCardBalance(Card card, BigDecimal balance) {
        try {
            card.setBalance(balance);
            return cardDatabase.updateCard(card);
        } catch (SQLException exception) {
            return false;
        }
    }

    public void updateUserTransactionsList() {
        user.getTransactions().add(transaction);
    }

    public boolean validateCardNumber(String cardNumber) {
        try {
            Card card = cardDatabase.getCardByCardNumber(cardNumber);
            return card != null;
        } catch (SQLException exception) {
            return false;
        }
    }

    public boolean validateCardPassword(String password) {
        return PasswordUtils.decodePassword(user.getCard().getPassword(), user.getCard().getCardNumber()).equals(password);
    }

    public boolean validateCardNumberPattern(String text) {
        return text.matches("\\d*") && text.length() == 16;
    }

    // TRANSACTION SECTION

    public boolean insertTransaction() {
        try {
            return transactionDatabase.insertTransaction(transaction);
        } catch (SQLException exception) {
            return false;
        }
    }

    public void createDeposit(Card senderCard, Card receiverCard, String note, BigDecimal amount) {
        String doer = receiverCard.getHolder();
        String trackingCode = generateTrackingCode();
        String sender = senderCard.getCardNumber();
        String receiver = receiverCard.getCardNumber();
        String dateTime = getCurrentDateTime();
        String transactionType = "DEPOSIT";

        transaction = new Transaction(doer, trackingCode, sender, receiver, note, dateTime, amount, transactionType);
    }

    public void createWithdrawal(Card senderCard, Card receiverCard, String note, BigDecimal amount) {
        String doer = senderCard.getHolder();
        String trackingCode = generateTrackingCode();
        String sender = senderCard.getCardNumber();
        String receiver = receiverCard.getCardNumber();
        String dateTime = getCurrentDateTime();
        String transactionType = "WITHDRAWAL";

        transaction = new Transaction(doer, trackingCode, sender, receiver, note, dateTime, amount, transactionType);
    }

    public BigDecimal calculateDeposit(BigDecimal balance, BigDecimal amount) {
        return balance.add(amount);
    }

    public BigDecimal calculateWithdrawal(BigDecimal balance, BigDecimal amount) {
        return balance.subtract(amount);
    }

    public String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());
    }

    public String generateTrackingCode() {
        String trackingCode;
        do {
            trackingCode = TextUtils.random(6, false, true);
        } while (!isNewTrackingCode(trackingCode));

        return trackingCode;
    }

    private boolean isNewTrackingCode(String trackingCode) {
        try {
            List<Transaction> transactions = transactionDatabase.getAllTransactions();
            for (Transaction transaction : transactions) {
                if (transaction.getTrackingCode().equals(trackingCode)) {
                    return false;
                }
            }
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    public boolean validateAmountPattern(String text) {
        return text.matches("\\d*") && !text.startsWith("0") && text.length() >= 4;
    }

    // GETTER & SETTER

    public User getUser() {
        return user;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
