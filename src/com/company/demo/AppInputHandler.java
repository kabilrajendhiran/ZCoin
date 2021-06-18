package com.company.demo;

import com.company.demo.account.AccountManagementInputHandler;
import com.company.demo.common.Utility;
import com.company.demo.data.Backend;
import com.company.demo.transaction.TransactionInputHandler;
import com.company.demo.user.UserManagement;
import com.company.demo.user.UserManagementInputHandler;
import com.company.demo.user.ZUser;

import java.util.Scanner;

/**
 * This class is responsible for handling the user input.
 * Every user have to use this class to proceed in the application.
 * In other words, this is a GUI less UI for this application.
 * */

public class AppInputHandler {

    private static ZUser currentUser;                                           // To emulate session environment
    private final UserManagement userManagement;                                // To access user management methods
    private final AccountManagementInputHandler accountManagementInputHandler;  // To access account management input handler methods
    private final UserManagementInputHandler userManagementInputHandler;        // To access user management input handler methods
    private final TransactionInputHandler transactionInputHandler;              // To access transaction management input handler methods
    private final Utility utility;                                              // To access utility methods


    public AppInputHandler()
    {
        Backend.prepareBackend();  /* This will declare and initialize empty the data structures for the application to work */
        userManagement = new UserManagement();
        accountManagementInputHandler = new AccountManagementInputHandler();
        userManagementInputHandler = new UserManagementInputHandler();
        transactionInputHandler = new TransactionInputHandler();
        utility = new Utility();
        appBootstrap();
    }

    /**
     * This method bootstraps the application backend with data
     * @see Backend
     * */
    private void appBootstrap()
    {
        ZUser admin = new ZUser("ram","admin","ram@gmail.com","9999988888",
                "#K@cd123",4.0);

        ZUser user = new ZUser("kabil","user","kabil@gmail.com","999977788",
                "#K@bd123",9.0);

        userManagement.register(admin);
        userManagement.register(user);
        admin.setLoggedInStatus(true);
        admin.setZCoins(15.0);
        userManagement.approveUser(admin.getEmailId());
        userManagement.approveUser(user.getEmailId());
        currentUser = admin;

    }

    /**
    * This method will start the execution of Input handling events of the application
    * This method calls helper methods according to the user input
    * Check below for the helper methods
    * {@link #userManagementHelper(Scanner)}
    * {@link #accountManagementHelper(Scanner)}
    * {@link #transactionManagementHelper(Scanner)}
    * */
    public void start()
    {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        String[] options = {"User Management","Account Management","Transaction Management","Exit"};
        System.out.println("ZCoin Project");
        System.out.println("Enter an option below");
        while (!exit)
        {
            utility.displayOptions(options,"ZCoin Project");
            int option = scanner.nextInt();

            switch (option){
                case 1:{
                    this.userManagementHelper(scanner);
                    break;
                }
                case 2:{
                    this.accountManagementHelper(scanner);
                    break;
                }
                case 3:{
                    this.transactionManagementHelper(scanner);
                    break;
                }
                case 4:{
                    exit = true;
                    System.out.println("Good bye!!!");
                    break;
                }
                default:{
                    break;
                }
            }

        }
    }


    /**
     * This method helps to get input from the user for User Management
     * @param scanner is used to get input from the user
     * @see UserManagement
     * */
    public void userManagementHelper(Scanner scanner)
    {
        boolean exit = false;
        String[] options = {"Register User","Login","Approve User","Exit"};

        while (!exit){
            utility.displayOptions(options,"User Management");
            int option = scanner.nextInt();
            switch (option)
            {
                case 1:{
                    userManagementInputHandler.registerUser();
                    break;
                }
                case 2:{
                    String res = userManagementInputHandler.Login();
                    if(!res.equals("failed"))
                    {
                        currentUser = userManagement.searchAndGetZUser(res);
                    }
                    else
                    {
                        System.out.println("Login failed");
                    }
                    break;
                }
                case 3:{
                    userManagementInputHandler.approveUser(currentUser);
                    break;
                }
                case 4:{
                    exit = true;
                    System.out.println("Good bye!!!");
                    break;
                }
                default:{
                    break;
                }
            }
        }


    }

    /**
     * This method helps to get input from the user for Account Management
     * @param scanner is used to get input from the user
     * @see com.company.demo.account.AccountManagement
     * */
    public void accountManagementHelper(Scanner scanner)
    {
        boolean exit = false;
        String[] options = {"Deposit RC","ZCoin Transaction","Convert Coins","Change Conversion Rate","Change Exchange Commission Rate",
                "Display Balance","Exit"};

        while (!exit)
        {
            utility.displayOptions(options,"Account Management");
            int option = scanner.nextInt();

            switch(option)
            {
                case 1:{
                    accountManagementInputHandler.depositRC(currentUser);
                    break;
                }
                case 2:{
                    accountManagementInputHandler.ZCoinTransaction(currentUser);
                    break;
                }
                case 3:{
                    accountManagementInputHandler.convertCoins(currentUser);
                    break;
                }
                case 4:{
                    accountManagementInputHandler.changeConversionRate();
                    break;
                }
                case 5:{
                    accountManagementInputHandler.changeZCoinExchangeCommissionRate();
                    break;
                }
                case 6:{
                    accountManagementInputHandler.displayBalance(currentUser);
                    break;
                }
                case 7:{
                    exit = true;
                    System.out.println("Good bye!!!");
                    break;
                }
                default:{
                    break;
                }
            }
        }


    }


    /**
     * This method helps to get input from the user for Transaction Management
     * @param scanner is used to get input from the user
     * @see com.company.demo.transaction.TransactionManagement
     * */
    public void transactionManagementHelper(Scanner scanner)
    {
        boolean exit = false;
        String[] options = {"Display all transactions","Display User transactions","Exit"};

        while (!exit)
        {
            utility.displayOptions(options,"Transaction Management");
            int option = scanner.nextInt();

            switch (option)
            {
                case 1:{
                    transactionInputHandler.displayAllTransaction(currentUser);
                    break;
                }
                case 2:{
                    transactionInputHandler.displayMyTransactions(currentUser);
                    break;
                }
                case 3:{
                    exit = true;
                    System.out.println("Good bye!!!");
                    break;
                }
                default:{
                    break;
                }
            }
        }

    }



}
