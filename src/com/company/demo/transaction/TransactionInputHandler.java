package com.company.demo.transaction;

import com.company.demo.user.UserManagement;
import com.company.demo.user.ZUser;

/**
 * This class maybe removed later
 * This class is responsible for handling the inputs for {@link TransactionManagement transaction management}.
 * @see UserManagement
 * */
public class TransactionInputHandler {


    private final UserManagement userManagement;
    private final TransactionManagement transactionManagement;

    public TransactionInputHandler()
    {
        userManagement = new UserManagement();
        transactionManagement = new TransactionManagement();
    }

    /**
     * This method is responsible validating user before calling {@link TransactionManagement#displayAllTransactions() displayAllTransactions()}
     * in transaction management
     * @param loggedInUser should be a valid and loggedIn user
     * */
    public void displayAllTransaction(ZUser loggedInUser)
    {
        if(userManagement.isAuthorized(loggedInUser))
        {
            transactionManagement.displayAllTransactions();
        }
        else
        {
            System.out.println("Not Authorized");
        }
    }

    /**
     * This method is responsible validating user before calling {@link TransactionManagement#displayTransactionForOneUser(String) displayTransactionForOneUser(String)}
     * in transaction management
     * @param loggedInUser should be a valid and loggedIn user
     * */
    public void displayMyTransactions(ZUser loggedInUser)
    {
        if(loggedInUser.isApproved())
        {
            transactionManagement.displayTransactionForOneUser(loggedInUser.getZId());
        }
        else
        {
            System.out.println("Not Authorized");
        }
    }
}
