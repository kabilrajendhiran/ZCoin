package com.company.demo.user;

import com.company.demo.common.Utility;
/**
* This class is responsible for creating user objects. This is a model class which have getters and setters for its fields
* This class uses {@link Utility Utility} class methods for generating HID and ZID.
* @see Utility#H_IDgenerator()
* @see Utility#ZIDgenerator()
* */
public class ZUser {

    private String fullName;
    private String userType;
    private String emailId;
    private String mobileNumber;
    private boolean isApproved;
    private String H_Id;
    private String password;
    private double realCoins;
    private String ZId;
    private double ZCoins;
    private boolean loggedInStatus;

    public ZUser(String fullName, String userType, String emailId, String mobileNumber, String password, double realCoins) {
        this.fullName = fullName;
        this.userType = userType;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
        this.isApproved = false;
        this.H_Id = Utility.H_IDgenerator();
        this.password = password;
        this.realCoins = realCoins;
        this.ZId = "";
        this.ZCoins = 0.0;
        this.loggedInStatus = false;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getH_Id() {
        return H_Id;
    }

    public void setH_Id(String h_Id) {
        H_Id = h_Id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getRealCoins() {
        return realCoins;
    }

    public void setRealCoins(double realCoins) {
        this.realCoins = realCoins;
    }

    public String getZId() {
        return ZId;
    }

    public void setZId(String ZId) {
        this.ZId = ZId;
    }

    public double getZCoins() {
        return ZCoins;
    }

    public void setZCoins(double ZCoins) {
        this.ZCoins = ZCoins;
    }

    public boolean isLoggedInStatus() {
        return loggedInStatus;
    }

    public void setLoggedInStatus(boolean loggedInStatus) {
        this.loggedInStatus = loggedInStatus;
    }


    @Override
    public String toString() {
        return "ZUser{" +
                "fullName='" + fullName + '\'' +
                ", userType='" + userType + '\'' +
                ", emailId='" + emailId + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", isApproved=" + isApproved +
                ", H_Id='" + H_Id + '\'' +
                ", password='" + password + '\'' +
                ", realCoins=" + realCoins +
                ", ZId='" + ZId + '\'' +
                ", ZCoins=" + ZCoins +
                ", loggedInStatus=" + loggedInStatus +
                '}';
    }
}
