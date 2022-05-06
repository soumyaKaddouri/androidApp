package com.example.androidapp.model;

public class User {
    private String fName, email, phoneNum, type;

    public User() {

    }

    public User(String fName, String email, String phoneNum, String type) {
        this.fName = fName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.type = type;
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
