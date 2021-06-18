package com.company.demo.data;

import com.company.demo.transaction.Transaction;
import com.company.demo.user.ZUser;

import java.util.HashMap;

/**
 * Since this application is not using database, this class is responsible for storing and maintaining data
 */
public class Backend {

    /**
     * This field stores the users in a HashMap
     * Key - ZID
     * Value - {@link ZUser User} object
     */
    private static final HashMap<String, ZUser> users = new HashMap<>();

    /**
     * This field stores the ZID and EmailID of users as Key and value respectively
     */
    private static final HashMap<String, String> zid_email = new HashMap<>();

    /**
     * This field stores the transactionId and {@link Transaction transaction} object in the hashmap as
     * key and value respectively
     */
    private static final HashMap<String, Transaction> transactions = new HashMap<>();

    /**
     * This field stores remitter and receiver ZID in the format remitterZID$receiverZID as key
     * and transactionID as value
     */
    private static final HashMap<String, String> users_transaction = new HashMap<>();

    /**
     * This field stores a default User account acts as an official bank account
     */
    private static final ZUser bank = new ZUser("Z-BANK", "admin", "zbank@zmail.com", "8888800000", "Z@#1234", 1000.0);


    public static HashMap<String, ZUser> getUsers() {
        return users;
    }

    public static HashMap<String, String> getZid_email() {
        return zid_email;
    }

    public static HashMap<String, Transaction> getTransactions() {
        return transactions;
    }

    public static ZUser getBank() {
        return bank;
    }

    public static HashMap<String, String> getUsers_transaction() {
        return users_transaction;
    }

    /**
     * This method creates a basic data for application to proceed
     * This method add some ZCoins and approve the {@link #bank Official bank account}
     * Adds the bank to {@link #users users} field
     * Adds an entry to {@link #zid_email} field
     * */
    public static void prepareBackend() {
        bank.setZCoins(500.0);
        bank.setApproved(true);
        users.put(bank.getEmailId(), bank);
        zid_email.put(bank.getZId(), bank.getEmailId());
    }

}
