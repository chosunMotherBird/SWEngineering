package com.example.registerloginexample;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface initMyApi {
    // @통신 방식("통신 API명")

    @POST("http://10.0.2.2:8080/users/login")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    @POST("http://10.0.2.2:8080/users")
    Call<RegisterResponse> getRegisterResponse(@Body RegisterRequest registerRequest);
}
