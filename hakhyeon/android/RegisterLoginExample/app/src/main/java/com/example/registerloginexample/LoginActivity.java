package com.example.registerloginexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    private initMyApi initMyApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        // 회원가입 버튼 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                // 로그인 정보 미입력 시
                if (email.trim().length() == 0 || userPass.trim().length() == 0 || email == null || userPass == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("알림")
                            .setMessage("로그인 정보를 입력바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                } else {
                    LoginResponse();
                }
            }
        });
    }

    public void LoginResponse() {
        String email = et_id.getText().toString().trim();
        String userPass = et_pass.getText().toString().trim();

        // loginRequest에 사용자가 입력한 email, pw를 저장
        LoginRequest loginRequest = new LoginRequest(email, userPass);

        // retrofit 생성
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();

        // loginRequest에 저장된 데이터와 함께 init에서 정의한 getLoginResponse 함수를 실행한 후 응답을 받음
        initMyApi.getLoginResponse(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("retrofit", "Data fetch success");

                // 통신 성공
                if (response.isSuccessful() && response.body() != null) {

                    // response.body()를 result에 저장
                    LoginResponse result = response.body();

                    // 받은 코드 저장
                    String status = result.getStatusCode();
                    // 받은 토큰 저장
                    String token = result.getToken();

                    String success = "200"; // 로그인 성공
                    String errorInput = "404"; // user not found

                    String email = et_id.getText().toString();
                    String userPass = et_pass.getText().toString();

                    // 다른 통신을 하기 위해 token 저장
                    setPreference(token, token);

                    Toast.makeText(LoginActivity.this, email + "님 환영합니다.", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("email", email);
//                    startActivity(intent);
//                    LoginActivity.this.finish();

                } else {
                    Toast.makeText(LoginActivity.this, "계정이 존재하지 않습니다. 다시 로그인 하세요", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    LoginActivity.this.finish();
                }
            }

            // 통신 실패
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }

    // 데이터를 내부 저장소에 저장하기
    public void setPreference(String key, String value) {
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // 내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }
}