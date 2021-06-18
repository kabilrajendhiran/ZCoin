package com.company.demo.transaction;

import com.company.demo.common.Utility;

/**
 * This class is responsible for creating transaction object to store information about a transaction.
 * This is a model class, there is nothing more than fields and getter methods.
 * Since a transaction is a one time process and it can't be modified, the fields in the transaction except status are final.
 * */
public class Transaction {
    private final String transactionId;
    private final String senderName;
    private final String senderZID;
    private final String receiverName;
    private final String receiverZID;
    private final String message;
    private final double exchangeAmount;
    /**
     * @deprecated This field maybe changed from boolean into enum.
     * */
    @Deprecated
    private boolean status;

    public Transaction(String senderName, String senderZID, String receiverName, String receiverZID, String message,
                       double exchangeAmount, boolean status) {
        this.transactionId = Utility.TransactionIDgenerator();
        this.senderName = senderName;
        this.senderZID = senderZID;
        this.receiverName = receiverName;
        this.receiverZID = receiverZID;
        this.message = message;
        this.exchangeAmount = exchangeAmount;
        this.status = status;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderZID() {
        return senderZID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverZID() {
        return receiverZID;
    }

    public String getMessage() {
        return message;
    }

    public double getExchangeAmount() {
        return exchangeAmount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction").append('\n');
        sb.append("transactionId  = ").append(transactionId).append('\n');
        sb.append("senderName     = ").append(senderName).append('\n');
        sb.append("senderZID      = ").append(senderZID).append('\n');
        sb.append("receiverName   = ").append(receiverName).append('\n');
        sb.append("receiverZID    = ").append(receiverZID).append('\n');
        sb.append("message        = ").append(message).append('\n');
        sb.append("exchangeAmount = ").append(exchangeAmount).append('\n');
        if(status) {
            sb.append(", status=").append("Success");
        }
        else
        {
            sb.append(", status=").append("Failure");
        }
        sb.append('\n');
        return sb.toString();
    }
}
