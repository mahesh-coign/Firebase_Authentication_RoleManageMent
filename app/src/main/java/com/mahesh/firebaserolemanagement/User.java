package com.mahesh.firebaserolemanagement;

public class User {

    private  String name,email,mobileNumber,userType;

    public User() {
    }

    public User(String name, String email, String mobileNumber,String userType) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
