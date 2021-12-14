package com.aminnorouzi.atm.model;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private Card card;
    private List<Transaction> transactions;

    public User(String username, String password, String fullName, String email, Card card, List<Transaction> transactions) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.card = card;
        this.transactions = transactions;
    }

    public User(String username, String password, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", card=" + card +
                ", transactions=" + transactions +
                '}';
    }
}
