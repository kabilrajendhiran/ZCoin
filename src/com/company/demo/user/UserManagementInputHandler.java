package com.company.demo.user;

import com.company.demo.common.Utility;
import java.util.Scanner;

public class UserManagementInputHandler {

    private final Utility utility;
    private final UserManagement userManagement;


    public UserManagementInputHandler()
    {
        this.utility = new Utility();
        this.userManagement = new UserManagement();
    }


    /**
     * This method is responsible for collecting data for the method {@link UserManagement#register(ZUser)}
     */
    public void registerUser()
    {
        String[] actypes = {"admin","user"};
        System.out.println("Register User");
        Scanner scanner = new Scanner(System.in);


        System.out.println("Name");
        String name = scanner.next();

        String accType = utility.getOptionFromUser(actypes,"Account Types");

        System.out.println("Email Id");
        String email = scanner.next();
        System.out.println("Mobile Number");
        String mobileNumber = scanner.next();
        System.out.println("Password");
        String password = getPasswordFromUser(scanner,name,email,mobileNumber);
        System.out.println("Initial RC");
        double rc = scanner.nextDouble();

        ZUser zUser = new ZUser(name,accType,email,mobileNumber,password,rc);
        userManagement.register(zUser);

    }

    /**
     * This method is responsible for collecting data for the method {@link UserManagement#login(String, String)}
     * @return emailID of the user if login success else returns "failed" as String
     */
    public String Login()
    {
        boolean exit = false;
        System.out.println("Login");
        Scanner scanner = new Scanner(System.in);
        String email="";
        while (!exit)
        {
            System.out.println("Enter email");
            email = scanner.next();
            System.out.println("Enter password");
            String password = scanner.next();

            if(userManagement.login(email,password))
            {
                break;
            }
            else
            {
                System.out.println("Incorrect credentials or user not approved");
                exit = utility.retryOrExit("Login");
                if(exit)
                {
                    email = "failed";
                }
            }
        }
        return email;
    }

    /**
     * This method is responsible for collecting data for the method {@link UserManagement#approveUser(String)}
     * @param loggedInUser is the {@link ZUser ZUser} object whose loggedInStatus is set to true
     */
    public void approveUser(ZUser loggedInUser)
    {
        if(!checkUser(loggedInUser))
        {
            System.out.println("No User logged in");
            return;
        }
        else if(!userManagement.isAuthorized(loggedInUser))
        {
            System.out.println("You are not authorized for this operation");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Approve User");
        boolean exit = false;
        while (!exit)
        {
            System.out.println("Enter email");
            String email = scanner.next();
            if(userManagement.approveUser(email))
            {
                exit = true;
            }
            else
            {
                exit = utility.retryOrExit("User Approval");
            }

            if(!exit)
            {
                System.out.println("Approval failed :(");
            }
        }
    }

    /**
     * @deprecated
     * This method is responsible for check whether the user is loggedIn or Not
     * This method usage can be avoided by proper handling of {@link ZUser ZUser} object.
     * @param loggedInUser is the {@link ZUser ZUser} object.
     * @return true or false
     */
    @Deprecated
    private boolean checkUser(ZUser loggedInUser)
    {
        if(loggedInUser ==null)
        {
            return false;
        }
        return loggedInUser.isLoggedInStatus();
    }

    /**
     * This method is responsible for getting valid password from the user. This method is a tool for {@link #registerUser()}
     * This uses {@link Utility#passwordValidator(String, String, String, String)} for validating password
     * @param scanner is a Scanner object for getting input from the user
     * @param name is full name of the {@link ZUser ZUser}
     * @param email should be a valid emailID
     * @param mob should be a valid mobile number
     * @return password as a String
     * */
    private String getPasswordFromUser(Scanner scanner, String name, String email, String mob)
    {
        boolean flag = false;
        String password = "";
        while (!flag)
        {
            password = scanner.next();
            flag = utility.passwordValidator(name, email, mob, password);
        }
        return password;
    }


}
