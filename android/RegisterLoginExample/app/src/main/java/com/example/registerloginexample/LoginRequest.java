package com.example.registerloginexample;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

// 서버에 보낼 데이터 email, password를 서버에 보내줌.
public class LoginRequest {

    @SerializedName("email")
    public String email;

    @SerializedName("userPass")
    public String userPass;

    public LoginRequest(String email, String userPass) {
        this.email = email;
        this.userPass = userPass;
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
}
