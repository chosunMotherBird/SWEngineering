package com.example.project6.SignUpPage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project6.R;
import com.example.project6.User.UserDTO;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private Button signUpBtn;
    private EditText email_edt;
    private EditText password_edt;
    private EditText name_edt;
    private EditText phone_edt;
    private String email;
    private String password;
    private String name;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUpBtn=findViewById(R.id.signUp_btn_inSignUp);
        email_edt=findViewById(R.id.email_editText_inSignUp);
        password_edt=findViewById(R.id.password_editText_inSignUp);
        name_edt=findViewById(R.id.name_editText);
        phone_edt=findViewById(R.id.phone_editText);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern pattern= Patterns.EMAIL_ADDRESS;
                email=String.valueOf(email_edt.getText());
                password=String.valueOf(password_edt.getText());
                name=String.valueOf(name_edt.getText());
                phone=String.valueOf(phone_edt.getText());
                UserDTO userDTO=new UserDTO(email, password, name, phone);
                if(email.isEmpty()|| password.isEmpty() || name.isEmpty() || phone.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력하세요", Toast.LENGTH_LONG).show();
                }
                else if(!pattern.matcher(email).matches())
                {
                    Toast.makeText(getApplicationContext(), "이메일형식에 맞게 입력하세요", Toast.LENGTH_LONG).show();
                }
                else{
                    SignUpRequestThread signUpRequestThread=new SignUpRequestThread(userDTO);
                    Thread tt=new Thread(signUpRequestThread);
                    tt.start();
                    try {
                        tt.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int result=signUpRequestThread.getResult();
                    if(result==201)
                        Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
                    else if(result==409)
                        Toast.makeText(getApplicationContext(), "중복된 이메일입니다.", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "알 수 없는 오류발생", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class SignUpRequestThread extends Thread {
        private int result;
        private UserDTO userDTO;
        public SignUpRequestThread(UserDTO userDTO){this.userDTO= userDTO;}
        @Override
        public void run() {
            result = new SignUpRequest().requestSignUp("http://192.168.0.2:8088/users",userDTO);
        }

        public int getResult() {
            return this.result;
        }
    }

}