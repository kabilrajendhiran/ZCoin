package com.company.demo.account;

import com.company.demo.data.Backend;
import com.company.demo.transaction.TransactionManagement;
import com.company.demo.user.UserManagement;
import com.company.demo.user.ZUser;


/**
 * This class is responsible for all the account related actions for this application
 */
public class AccountManagement {

    /**
     * This value is responsible for converting RC-ZC value
     */
    private static double RC_TO_ZCOIN_RATE = 2.0;

    /**
     * This value is responsible for converting ZC-RC value
     */
    private static double ZCOIN_COMMISSION_RATE = 0.15;

    private final UserManagement userManagement;
    private final TransactionManagement transactionManagement;


    public AccountManagement()
    {
        userManagement = new UserManagement();
        transactionManagement = new TransactionManagement();
    }

    /**
     * This method is resposible for transferring ZCoins from one's account to another
     * @param loggedInUser should be a valid and loggedIn user
     * @param toZID should be a valid ZID belonging to who you used to send ZCoins
     * @param coins amount of coins to be sent
     * @return boolean value
     * @see TransactionManagement#logTransaction(String, String, String, String, String, double, boolean)
     * @see #transaction(String, String, double, ZUser)
     * */
    public boolean ZCoinTransaction(ZUser loggedInUser, String toZID, double coins)
    {
        ZUser user = null;
        String coinType = "ZC";
        boolean deposit = false;
        boolean withdraw = transaction("withdraw",coinType,coins,loggedInUser);

        if(withdraw)
        {
            user = userManagement.getUserFromZID(toZID);
            deposit = transaction("deposit",coinType,coins,user);
        }

        if(user!=null)
        {
            transactionManagement.logTransaction(loggedInUser.getFullName(), loggedInUser.getZId(), user.getFullName(), user.getZId(),
                    "ZCoin Transaction", coins, withdraw && deposit);
        }

        return (withdraw && deposit);
    }

    /**
     * This method is resposible for depositing Real coins to the user's account
     * This simply get the previous balance by using {@link ZUser#getRealCoins() getRealCoins()} and add the deposit amount
     * to it and update the balance using {@link ZUser#setRealCoins(double) setRealCoins(double)}
     * @param loggedInUser should be a valid and loggedIn user
     * @param rc is the amount of real coins to be deposited
     * @return boolean value
     * @see #transaction(String, String, double, ZUser)
     * @see TransactionManagement#logTransaction(String, String, String, String, String, double, boolean)
     * */
    public boolean depositRC(ZUser loggedInUser, double rc)
    {
        boolean status = transaction("deposit","RC",rc,loggedInUser);
        transactionManagement.logTransaction(loggedInUser.getFullName(), loggedInUser.getZId(), Backend.getBank().getFullName(),Backend.getBank().getZId(),"Deposit RC",
                rc,status);
        return status;
    }

    /**
     * This method is responsible for converting ZC into RC with the help of {@link #calculateRC(double) calculateRC(double)}
     * @param loggedInUser should be a valid and loggedIn user
     * @param zc is the amount of Z coins to be deposited
     * @return boolean value
     * @see #transaction(String, String, double, ZUser)
     * @see TransactionManagement#logTransaction(String, String, String, String, String, double, boolean)
     * */
    public boolean convertZCToRC(ZUser loggedInUser, double zc)
    {
        boolean status = transaction("withdraw","ZC",zc,loggedInUser);

        transactionManagement.logTransaction(loggedInUser.getFullName(), loggedInUser.getZId(), Backend.getBank().getFullName(),Backend.getBank().getZId(),"Withdraw ZC",
                zc,status);
        if(!status)
        {
            return false;
        }
        double rc = calculateRC(zc);
        status = transaction("deposit","RC",rc,loggedInUser);

        transactionManagement.logTransaction(Backend.getBank().getFullName(),Backend.getBank().getZId(), loggedInUser.getFullName(), loggedInUser.getZId(), "Deposit RC",
                rc,status);
        if(!status)
        {
            return false;
        }
        System.out.println("Amount deposited : "+ rc);
        return true;
    }

    /**
     * This method is responsible for calculating Real Coins from given ZCoins
     * @param zc is the amount of ZCoins used for calculating Real Coins
     * @return real coins
     * @see #calculateAdditionalCharges(double)
     * @see #exchangeZCoinToRC(double)
     * */
    private double calculateRC(double zc)
    {
        double rc = exchangeZCoinToRC(zc);
        double extraChargesInZCoins = calculateAdditionalCharges(zc);
        double extraChargesInRCoins = exchangeZCoinToRC(extraChargesInZCoins);

        rc = rc - extraChargesInRCoins;
        System.out.println("Additional Charges : "+ extraChargesInRCoins);
        return rc;
    }

    /**
     * This method is responsible for converting Real coins to Z Coins
     * @param loggedInUser should be a valid and loggedIn user
     * @param rc is the amount of Real coins to be deposited
     * @return boolean value
     * @see #transaction(String, String, double, ZUser)
     * @see TransactionManagement#logTransaction(String, String, String, String, String, double, boolean)
     * @see #exchangeRCtoZCoin(double)
     * */
    public boolean convertRCToZC(ZUser loggedInUser, double rc)
    {
        boolean status = transaction("withdraw","RC",rc,loggedInUser);
        transactionManagement.logTransaction(loggedInUser.getFullName(), loggedInUser.getZId(), Backend.getBank().getFullName(),Backend.getBank().getZId(),"Withdraw RC",
                rc,status);

        if(!status)
        {
            return false;
        }
        double zc = exchangeRCtoZCoin(rc);

        status = transaction("deposit","ZC",zc,loggedInUser);
        transactionManagement.logTransaction(Backend.getBank().getFullName(),Backend.getBank().getZId(), loggedInUser.getFullName(), loggedInUser.getZId(), "Deposit ZC",
                zc,status);

        if(!status)
        {
            return false;
        }

        return true;
    }

    /**
     * This method is responsible for executing any transaction in this application
     * @param transactionType defines that the transaction is withdraw or deposit
     * @param coinType defines that the coins are ZCoin or Real Coin
     * @param amount defines the amount of coin used in the transaction
     * @param user {@link ZUser User} who's account is used for withdraw or deposit.
     * @return boolean value
     * */
    public boolean transaction(String transactionType, String coinType, double amount, ZUser user)
    {
        if(transactionType.equals("withdraw"))
        {
            if(!checkBalanceForWithdrawal(user,coinType,amount))
            {
                System.out.println("Not enough balance");
                return false;
            }
            amount = -amount;
        }
        else if(!transactionType.equals("deposit"))
        {
            System.out.println("Transaction failed");
            return false;
        }
        if(coinType.equals("ZC"))
        {
            user.setZCoins(user.getZCoins()+ amount);
        }
        else if(coinType.equals("RC"))
        {
            user.setRealCoins(user.getRealCoins()+ amount);
        }
        return true;
    }

    /**
     * This method is used to check the balance is enough for withdraw transaction
     * @param user {@link ZUser user} whose balance is checked
     * @param coinType defines the type of coin's balance need to be checked
     * @param amount defines the amount of coin requested for withdraw
     * @return boolean value
     * */
    public boolean checkBalanceForWithdrawal(ZUser user, String coinType, double amount)
    {
        double balance=0.0;
        if(coinType.equals("ZC"))
        {
            balance = user.getZCoins();
        }
        else if(coinType.equals("RC"))
        {
            balance = user.getRealCoins();
        }

        if(balance<amount)
        {
            return false;
        }
        return true;
    }

    /**
     * This method is responsible for changing the conversion rate for RC to ZC
     * @param diff is the amount of difference.
     * @see #RC_TO_ZCOIN_RATE
     * */
    public void changeConversionRate(double diff)
    {
        RC_TO_ZCOIN_RATE = RC_TO_ZCOIN_RATE + diff;
    }

    /**
     * This method is responsible for changing the commission rate for ZC to RC
     * @param diff is the amount of difference.
     * @see #ZCOIN_COMMISSION_RATE
     * */
    public void changeZCoinCommissionRate(double diff)
    {
        ZCOIN_COMMISSION_RATE = ZCOIN_COMMISSION_RATE + diff;
    }

    /**
     * This method performs the operation of converting RC to ZC
     * @param rc is the amount of Real coins to be converted to ZC
     * @return ZCoins
     * */
    private double exchangeRCtoZCoin(double rc)
    {
        return rc/RC_TO_ZCOIN_RATE;
    }

    /**
     * This method performs the basic operation of converting ZC to RC
     * @param zc is the amount of ZCoins to be converted to Real Coins
     * @return intermediate RC value
     * @see #calculateRC(double)
     * */
    private double exchangeZCoinToRC(double zc)
    {
        return zc*RC_TO_ZCOIN_RATE;
    }

    /**
     * This method is responsible for calculating the additional charges while converting ZC to RC
     * @param zc is the amount of ZCoins to be converted to Real coins
     * */
    private double calculateAdditionalCharges(double zc)
    {
        return (zc* ZCOIN_COMMISSION_RATE);
    }




}
