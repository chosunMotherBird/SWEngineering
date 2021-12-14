package com.example.project6.logIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project6.Charger.ChargerDTO;
import com.example.project6.Main.MainActivity;
import com.example.project6.R;
import com.example.project6.SignUp.SignUpRequest;
import com.example.project6.SignUp.SignupActivity;
import com.example.project6.User.UserDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private String email;
    private String password;
    private Button signUpBtn;
    private Button logInBtn;
    private EditText email_edt;
    private EditText password_edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        signUpBtn=findViewById(R.id.signUp_btn_inLogin);
        logInBtn=findViewById(R.id.login_btn_inLogIn);
        email_edt=findViewById(R.id.email_editText_inLogIn);
        password_edt=findViewById(R.id.password_editText_inLogIn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                email = String.valueOf(email_edt.getText());
                password = String.valueOf(password_edt.getText());
                UserDTO userDTO = new UserDTO(email, password, "", "");
                if (email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력하세요", Toast.LENGTH_LONG).show();
                }
                else if (!pattern.matcher(email).matches())
                {
                    Toast.makeText(getApplicationContext(), "이메일형식에 맞게 입력하세요", Toast.LENGTH_LONG).show();
                }
                else {
                    LogInRequestThread logInRequestThread = new LogInRequestThread(userDTO);
                    Thread t = new Thread(logInRequestThread);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String result = logInRequestThread.getResult();
                    if (result.equals("404"))
                    {
                        Toast.makeText(getApplicationContext(), "존재하지 않는 유저", Toast.LENGTH_LONG).show();
                    }
                    else if (result.equals("400"))
                    {
                        Toast.makeText(getApplicationContext(), "아이디 패스워드 확인바람", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
                        UserDTO resultUserDTO=makeUserDTO(result);
                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userDTO", resultUserDTO);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }

    public class LogInRequestThread extends Thread {
        private String result;
        private UserDTO userDTO;
        public LogInRequestThread(UserDTO userDTO){this.userDTO= userDTO;}
        @Override
        public void run() {
            result = new LoginRequest().requestLogin("http://192.168.0.2:8088/users/login",userDTO);
        }

        public String getResult() {
            return this.result;
        }
    }

    public UserDTO makeUserDTO(String result){
        UserDTO userDTO=null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("userPass");
            String name = jsonObject.getString("userName");
            String phone = jsonObject.getString("phoneNum");
            userDTO=new UserDTO(email, password, name, phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userDTO;
    }
}
