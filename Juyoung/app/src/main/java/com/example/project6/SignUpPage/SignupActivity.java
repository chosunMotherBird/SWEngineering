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
            /**
             * 회원가입을 위한 버튼리스너
             * 1.editText 에서 받은 정보로 userDTO를 만듦.
             * 2.만약 입력되지 않은 정보가 있다면 error
             * 3.이메일이 이메일형식이 아니라면 error
             * 4.회원가입 요청, 회원가입처리는 오로지 responseCode 에 의존 로그인처럼 요청 시 return 값을 userDTO 를 받지 않도록 설정함.
             * 4-1. 201 회원가입 성공
             * 4-2. 409 중복된 이메일
             * 4-3. 나머지 오류는 알 수 없는 오류임. 만약 발생 시 responseCode 를 디버그로 확인 후 처리해야 함.
             * @param v signUpBtn
             */
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

    /**
     * 회원가입 비동기 처리를 위한 class
     */
    private class SignUpRequestThread extends Thread {

        private int result;
        private UserDTO userDTO;
        public SignUpRequestThread(UserDTO userDTO){this.userDTO= userDTO;}
        @Override
        public void run() {
            result = new SignUpRequest().requestSignUp("http://192.168.0.2:8088/users",userDTO);
        }

        /**
         * 리스폰스코드 리턴
         * @return ResponseCode
         */
        public int getResult() {
            return this.result;
        }
    }

}