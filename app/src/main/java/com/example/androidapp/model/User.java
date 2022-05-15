package com.example.androidapp.model;

public class User {
    private String fName, email, phoneNum, type;
    boolean isCord;
    public User() {

    }

    public User(String fName, String email, String phoneNum, String type,boolean isCord) {
        this.fName = fName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.type = type;
        this.isCord = isCord;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCord() {
        return isCord;
    }

    public void setCord(boolean cord) {
        isCord = cord;
    }

    @Override
    public String toString() {
        return "User{" +
                "fName='" + fName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
