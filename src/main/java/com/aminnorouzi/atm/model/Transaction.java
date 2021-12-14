package com.aminnorouzi.atm.model;

import java.math.BigDecimal;

public class Transaction {
    private String doer;
    private String trackingCode;
    private String sender;
    private String receiver;
    private String note;
    private String dateTime;
    private BigDecimal amount;
    private String transactionType;

    public Transaction(String doer, String trackingCode, String sender, String receiver, String note, String dateTime, BigDecimal amount, String transactionType) {
        this.doer = doer;
        this.trackingCode = trackingCode;
        this.sender = sender;
        this.receiver = receiver;
        this.note = note;
        this.dateTime = dateTime;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public String getDoer() {
        return doer;
    }

    public void setDoer(String doer) {
        this.doer = doer;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "doer='" + doer + '\'' +
                ", trackingCode='" + trackingCode + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", note='" + note + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}