package com.example.androidapp.model;

public class Student {
    private String fullName;
    private String email;
    private String tel;

    public Student(String fullName, String email, String tel) {
        this.fullName = fullName;
        this.email = email;
        this.tel = tel;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
