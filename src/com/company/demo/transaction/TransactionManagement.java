package com.company.demo.transaction;

import com.company.demo.data.Backend;

import java.util.HashMap;

/**
 * This class is responsible for displaying and managing transaction details
 * */
public class TransactionManagement {

    /**
     * This method is responsible for displaying all transactions
     * @see Backend
     * */
    public void displayAllTransactions()
    {
        HashMap<String,Transaction> transactions = Backend.getTransactions();

        for (Transaction t: transactions.values()) {
            System.out.println(t);
        }
    }

    /**
     * This method is responsible for displaying transactions for single user
     * @param ZID should be a valid User's ZID
     * @see Backend
     * */
    public void displayTransactionForOneUser(String ZID)
    {
        HashMap<String,Transaction> transactions = Backend.getTransactions();
        HashMap<String,String> map = Backend.getUsers_transaction();

        for (String zid$zid: map.keySet()) {
            if(zid$zid.contains(ZID))
            {
                System.out.println(transactions.get(map.get(zid$zid)));
            }
        }

    }

    /**
     * This method is responsible for logging all transactions.
     * All success and failure transactions logged here
     * @param remitterName is the name of the person who sends coins
     * @param remitterZID is the ZID of the person who sends coins
     * @param receiverName is the name of the person who receives coin
     * @param receiverZID is the ZID of the person who receives coins
     * @param message contains additional information about the transaction
     * @param amount used in the transaction
     * @param status defines that the transaction is failure or success
     * */
    public void logTransaction(String remitterName, String remitterZID, String receiverName, String receiverZID,
                                String message, double amount, boolean status)
    {
        Transaction t = new Transaction(remitterName, remitterZID, receiverName, receiverZID, message, amount, status);
        HashMap<String,Transaction> transactions = Backend.getTransactions();
        transactions.put(t.getTransactionId(),t);
        Backend.getUsers_transaction().put((remitterZID+"$"+receiverZID),t.getTransactionId());

    }

}
