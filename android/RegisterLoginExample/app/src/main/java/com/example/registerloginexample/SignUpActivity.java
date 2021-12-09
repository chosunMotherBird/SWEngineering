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

public class SignUpActivity extends AppCompatActivity {
    private EditText et_id, et_pass, et_name, et_phone;
    private Button btn_register;
    private initMyApi initMyApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                String phoneNum = et_phone.getText().toString();

                // 회원가입 정보 미입력 시
                if (email.trim().length() == 0 || userPass.trim().length() == 0 || userName.trim().length() == 0 || phoneNum.trim().length() == 0 ||
                        email == null || userPass == null || userName == null || phoneNum == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("알림")
                            .setMessage("회원가입 정보를 입력바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                } else {
                    RegisterResponse();
                }
            }
        });
    }

    public void RegisterResponse() {
        String email = et_id.getText().toString().trim();
        String userPass = et_pass.getText().toString().trim();
        String userName = et_name.getText().toString().trim();
        String phoneNum = et_phone.getText().toString().trim();

        // RegisterRequest에 사용자가 입력한 내용들을 저장
        RegisterRequest registerRequest = new RegisterRequest(email, userPass, userName, phoneNum);

        // retrofit 생성
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();

        // loginRequest에 저장된 데이터와 함께 init에서 정의한 getLoginResponse 함수를 실행한 후 응답을 받음
        initMyApi.getRegisterResponse(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                Log.d("retrofit", "Data fetch success");

                // 통신 성공
                if (response.isSuccessful() && response.body() != null) {

                    // response.body()를 result에 저장
                    RegisterResponse result = response.body();

                    // 받은 코드 저장
                    String status = result.getStatusCode();
                    // 받은 토큰 저장
                    String token = result.getToken();

//                    String success = "201"; // 회원가입 성공
//                    String errorEmail = "301"; // 이메일 일치x
//                    String errorPw = "302"; // 비밀번호 일치x
//                    String errorName = "303"; // 이름 일치x
//                    String errorPhone = "304"; // 전화번호 일치x


                    // 다른 통신을 하기 위해 token 저장
                    setPreference(token, token);

                    Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                        intent.putExtra("email", email);
                    startActivity(intent);
                    SignUpActivity.this.finish();

//                    if (status.equals(success)) {
//                        String email = et_id.getText().toString();
//                        String userPass = et_pass.getText().toString();
//                        String userName = et_name.getText().toString();
//                        String phoneNum = et_phone.getText().toString();
//
//                        // 다른 통신을 하기 위해 token 저장
//                        setPreference(token, token);
//
//                        Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
////                        intent.putExtra("email", email);
//                        startActivity(intent);
//                        SignUpActivity.this.finish();
//
//                    } else if (status.equals(errorEmail)) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
//                        builder.setTitle("알림")
//                                .setMessage("아이디가 존재하지 않습니다.")
//                                .setPositiveButton("확인", null)
//                                .create()
//                                .show();
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//
//                    } else if (status.equals(errorPw)) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
//                        builder.setTitle("알림")
//                                .setMessage("패스워드가 존재하지 않습니다.")
//                                .setPositiveButton("확인", null)
//                                .create()
//                                .show();
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//
//                    } else if (status.equals(errorName)) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
//                        builder.setTitle("알림")
//                                .setMessage("이름이 존재하지 않습니다.")
//                                .setPositiveButton("확인", null)
//                                .create()
//                                .show();
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//
//                    } else if (status.equals(errorPhone)) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
//                        builder.setTitle("알림")
//                                .setMessage("전화번호가 존재하지 않습니다.")
//                                .setPositiveButton("확인", null)
//                                .create()
//                                .show();
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
//                        builder.setTitle("알림")
//                                .setMessage("예기치 못한 오류가 발생하였습니다.")
//                                .setPositiveButton("확인", null)
//                                .create()
//                                .show();
//                    }
                }
            }

            // 통신 실패
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
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