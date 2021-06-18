package com.company.demo.user;

import com.company.demo.common.Utility;
import com.company.demo.data.Backend;

import java.util.HashMap;


/**
 * This class is responsible for maintaining the user objects in the backend.
 * This class supports all the user related operations for this application.
 */
public class UserManagement {

    private final HashMap<String,ZUser> users;
    private final HashMap<String,String> zid_email;
    public UserManagement()
    {
        users = Backend.getUsers();
        zid_email = Backend.getZid_email();
    }


    /**
     * This method is responsible for login a user by validating the email and password
     * @param email should be a  valid email ID
     * @param password should be a valid password
     * A user must be registered and approved by admin before login.
     * {@link #approveUser(String)} to check the approve user implementation
     * @return boolean value
     * */
    public boolean login(String email, String password)
    {
        if(isUserExist(email,false))
        {
            ZUser user = users.get(email);
            if (user.getPassword().equals(password))
            {
                user.setLoggedInStatus(true);
                System.out.println("Successfully logged in...");
                return true;
            }
            else
            {
                System.out.println("Wrong password !!!");
                return false;
            }
        }
        return false;
    }


    /**
     * This method is responsible for registering the user.
     * This methods creates {@link ZUser object} and store in in {@link Backend} users HashMap
     * When the user is registered the ZID field is empty.
     * ZID is created when the user was approved by the admin
     * For more info check see also
     * @param user should be a valid user object
     * @see #approveUser(String)
     * @see Utility#ZIDgenerator()
     * */
    public void register(ZUser user)
    {
       if(isUserExist(user.getEmailId(),true))
       {
           System.out.println("User already exist");
           return;
       }
       users.put(user.getEmailId(), user);
       System.out.println("User Registered successfully");
    }


    /**
     * This method is resposible for updating the user object's approval status to true if user exists.
     * @param email should be a valid emailID of an already registered user
     * @see #isUserExist(String, boolean)
     * @return boolean value
     * */
    public boolean approveUser(String email)
    {
        if (isUserExist(email,false))
        {
            return false;
        }

        ZUser user = searchAndGetZUser(email);
        user.setApproved(true);
        String zid = Utility.ZIDgenerator();
        user.setZId(zid);
        zid_email.put(zid,user.getEmailId());
        return true;
    }


    /**
     * This method is used to check whether the user already exist or not exist.
     * If flag is true then this method displays that the user is already exist.
     * If flag is false then this method displays that the user is not exist.
     * For example:- {@link #login(String, String)} the flag is set to false to check that the user is exist for the given Email ID. Otherwise it displays "No User found".
     * For example:- {@link #register(ZUser)} the flag is set to false to check that the user is not exist for the given Email ID. Otherwise it displays "User already exists".
     * @param email should be an EmailID.
     * @param flag is used to control the behaviour of the method
     * @return boolean value
     * */
    public boolean isUserExist(String email, boolean flag)
    {
        if(users.containsKey(email))
        {
            if(flag)
            {
                System.out.println("User already exist");
            }
            return true;
        }
        else
        {
            if(!flag)
            {
                System.out.println("No user is found for the following email Id");
                System.out.println(email);
            }
            return false;
        }
    }


    /**
     * This method is used to search and get the ZUser object from the {@link Backend} users HashMap
     * This method returns null if no user is found for the given email ID
     * @param email should be a valid emailID
     * @return {@link ZUser ZUser} , null
     * */
    public ZUser searchAndGetZUser(String email)
    {
        if(isUserExist(email,false))
        {
            return users.get(email);
        }
        return null;
    }


    /**
     * This method is used to search User object at {@link Backend} users HashMap
     * It uses {@link Backend} zid_email HashMap to find emailId of the user from ZID. Then it uses {@link #searchAndGetZUser(String)} to get the User object
     * @param ZID is an automatically generated valid ZID for searching the user
     * @return {@link ZUser ZUser}, null
     * */
    public ZUser getUserFromZID(String ZID)
    {
        String emailID = zid_email.get(ZID);
        if(emailID!=null)
        {
            return searchAndGetZUser(emailID);
        }
        return null;
    }


    /**
     * This method is used to check whether the user is authorized to do certain actions such as
     * {@link #approveUser(String) approveUser(String emailID)},
     * {@link com.company.demo.account.AccountManagement#changeZCoinCommissionRate(double) changeZCoinCommissionRate(double)},
     * {@link com.company.demo.account.AccountManagement#changeConversionRate(double) changeConversionRate(double)}
     * @param user should be an {@link ZUser ZUser} object
     * @return boolean value
     * */
    public boolean isAuthorized(ZUser user)
    {
        return user.isLoggedInStatus() && user.getUserType().equals("admin");
    }


}
