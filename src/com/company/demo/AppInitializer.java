package com.company.demo;

/**
 * Note: This application is not multithreaded and not using enums for types
 * It will be updated as soon as possible
 * This class is the start of the application
 * @author kabil
 * @version 1.0
 * */
public class AppInitializer {

    public static void main(String[] args) {
        AppInputHandler appInputHandler = new AppInputHandler();
        appInputHandler.start();
    }

}
