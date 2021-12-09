package com.example.registerloginexample;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("email")
    public String email;

    @SerializedName("userPass")
    public String userPass;

    @SerializedName("userName")
    public String userName;

    @SerializedName("phoneNum")
    public String phoneNum;

    public RegisterRequest(String email, String userPass, String userName, String phoneNum) {
        this.email = email;
        this.userPass = userPass;
        this.userName = userName;
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
