package com.company.demo.common;

import java.util.Random;
import java.util.Scanner;

/**
 * Note : For this class docs is not completed
 * This class is a helper class which provides common method that can be reused throught the application
 * */
public class Utility {

    public static String H_IDgenerator()
    {
        StringBuilder id = new StringBuilder("HID-");
        Random random = new Random();
        int[] raw_id = random.ints(97,122).limit(10).toArray();
        for (int i:raw_id) {
            id.append((char) i);
        }
        return id.toString();
    }

    public static String ZIDgenerator()
    {
        StringBuilder id = new StringBuilder("ZID-");
        Random random = new Random();
        int[] raw_id = random.ints(97,122).limit(4).toArray();
        for (int i:raw_id) {
            id.append((char) i);
        }
        return id.toString();
    }

    public static String TransactionIDgenerator()
    {
        StringBuilder id = new StringBuilder("TID-");
        Random random = new Random();
        int[] raw_id = random.ints(97,122).limit(4).toArray();
        for (int i:raw_id) {
            id.append((char) i);
        }
        return id.toString();
    }


    public boolean passwordValidator( String name, String email, String mob, String pass)
    {
        String userName = email.split("@")[0];
        if(pass.length()<8 || pass.contains(name) || pass.contains(mob) || pass.contains(userName))
        {
            System.out.println("Password does not match the requirements");
            return false;
        }

        return true;
    }

    public void displayOptions(String[] optionList, String heading)
    {
        System.out.println("============= "+heading+" =============");
        int count=1;

        for (String option:optionList) {
            System.out.println(count+". "+option);
            count++;
        }
    }

    public boolean retryOrExit(String heading)
    {
        String[] options = {"Retry","Exit"};
        String e= getOptionFromUser(options,heading);
        return e.equals(options[1]);
    }

    public String getOptionFromUser(String[] optionList, String heading)
    {
        Scanner scanner = new Scanner(System.in);
        displayOptions(optionList,heading);
        int option = scanner.nextInt();

        while (true)
        {
            if(!(option>=1 && option<=optionList.length))
            {
                System.out.println("Wrong choice !!!");
                displayOptions(optionList,heading);
                option = scanner.nextInt();
            }
            else
            {
                break;
            }
        }
        return optionList[option-1];
    }



}
