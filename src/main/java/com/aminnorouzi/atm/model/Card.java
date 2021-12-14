package com.aminnorouzi.atm.model;

import java.math.BigDecimal;

public class Card {
    private String holder;
    private String cardNumber;
    private String password;
    private BigDecimal balance;

    public Card(String holder, String cardNumber, String password, BigDecimal balance) {
        this.holder = holder;
        this.cardNumber = cardNumber;
        this.password = password;
        this.balance = balance;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Card{" +
                "holder='" + holder + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
