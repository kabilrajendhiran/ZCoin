package com.company.demo.account;

import com.company.demo.common.Utility;
import com.company.demo.user.UserManagement;
import com.company.demo.user.ZUser;

import java.util.Scanner;

/**
 * @see AccountManagementInputHandler
 * Handles the input for account management actions
 * */

public class AccountManagementInputHandler {

    private final Utility utility;
    private final UserManagement userManagement;
    private final AccountManagement accountManagement;

    public AccountManagementInputHandler()
    {
        utility = new Utility();
        userManagement = new UserManagement();
        accountManagement = new AccountManagement();
    }

    /**
     * This method is responsible for collecting data from the user for the method
     * {@link com.company.demo.account.AccountManagement#depositRC(ZUser, double)}
     * @param loggedInUser is the user whose loggedIn status is true in ZUser object
     * To check ZUser {@link com.company.demo.user.ZUser}
     * @see AccountManagement#depositRC(ZUser, double)
     * */
    public void depositRC(ZUser loggedInUser)
    {
        boolean exit = false;
        System.out.println("Deposit RC");
        System.out.println("Enter the amount to be deposited");
        Scanner scanner = new Scanner(System.in);

        while (!exit)
        {
            double rc  = scanner.nextDouble();
            exit = accountManagement.depositRC(loggedInUser,rc);
            if(!exit)
            {
                exit = utility.retryOrExit("Deposit RC");
            }
        }

    }


    /**
    * This method is responsible for collecting data from the user for the method
    * {@link com.company.demo.account.AccountManagement#ZCoinTransaction(ZUser, String, double)}
    * @param loggedInUser is the user whose loggedIn status is true in ZUser object
    * To check ZUser {@link com.company.demo.user.ZUser}
    * @see UserManagement#getUserFromZID(String)
    * */
    public void ZCoinTransaction(ZUser loggedInUser)
    {
        boolean exit = false;
        System.out.println("ZCoin Transaction");
        Scanner scanner = new Scanner(System.in);
        while (!exit)
        {
            System.out.println("Enter Zid");
            String zid = scanner.next();
            ZUser user = userManagement.getUserFromZID(zid);
            if(isUserNull(user))
            {
                System.out.println("No User found for this ZID");
                exit = utility.retryOrExit("ZCoin Transaction");

            }
            else
            {
                System.out.println("Enter the amount for transaction");
                double amount = scanner.nextDouble();
                exit = accountManagement.ZCoinTransaction(loggedInUser,zid,amount);
                if(!exit)
                {
                    System.out.println("Transaction failed");
                    exit = utility.retryOrExit("ZCoin Transaction");
                }
            }
        }
    }


    /**
     * This method is responsible for collecting data from the user for the method
     * This method work for both ZC-RC and for RC-ZC
     * {@link com.company.demo.account.AccountManagement#ZCoinTransaction(ZUser, String, double)}
     * @param loggedInUser is the user whose loggedIn status is true in ZUser object
     * To check ZUser {@link com.company.demo.user.ZUser}
     * */
    public void convertCoins(ZUser loggedInUser)
    {
        String[] options = {"ZC","RC"};
        String option = utility.getOptionFromUser(options,"Convert coins");

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit)
        {
            System.out.println("Enter amount");
            double amount= scanner.nextDouble();
            if(option.equals("ZC"))
            {
                exit = accountManagement.convertZCToRC(loggedInUser,amount);
            }
            else
            {
                exit = accountManagement.convertRCToZC(loggedInUser,amount);
            }

            if(exit)
            {
                System.out.println("Successfully converted");
                displayBalance(loggedInUser);
            }
            else
            {
                exit = utility.retryOrExit("Convert coins");
            }
        }
    }


    /**
     * This method is resposible for collecting data from the user for the method
     * {@link com.company.demo.account.AccountManagement#changeConversionRate(double)}
     * @see AccountManagement#changeConversionRate(double)
     * */
    public void changeConversionRate()
    {
        System.out.println("Change conversion rate");
        System.out.println("Enter difference");
        Scanner scanner = new Scanner(System.in);
        double diff = scanner.nextDouble();
        accountManagement.changeConversionRate(diff);
        System.out.println("Conversion rate changed successfully");
    }


    /**
     * This method is responsible for collecting data from the user for the method
     * {@link com.company.demo.account.AccountManagement#changeZCoinCommissionRate(double)}
     * @see AccountManagement#changeZCoinCommissionRate(double)
     * */
    public void changeZCoinExchangeCommissionRate()
    {
        System.out.println("Change commission rate");
        System.out.println("Enter difference");
        Scanner scanner = new Scanner(System.in);
        double diff = scanner.nextDouble();
        accountManagement.changeZCoinCommissionRate(diff);
        System.out.println("Commission rate changed successfully");
    }

    /**
     * This method is responsible for displaying ZCoin and Real Coin balance
     * @param user is the user whose balance is to be displayed
     * */
    public void displayBalance(ZUser user)
    {
        System.out.println("ZC balance "+user.getZCoins());
        System.out.println("RC balance "+user.getRealCoins());
    }

    /**
     * This method is to check the user object is null or not.
     * This method is useful when toZID is not exist in {@link com.company.demo.account.AccountManagement#ZCoinTransaction(ZUser, String, double)}
     * @param user should be a valid {@link ZUser ZUser}
     * @return boolean value
     * */
    public boolean isUserNull(ZUser user)
    {
        return user==null;
    }

}
