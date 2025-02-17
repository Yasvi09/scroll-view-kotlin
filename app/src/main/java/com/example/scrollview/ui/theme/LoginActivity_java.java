package com.example.scrollview.ui.theme;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.scrollview.R;

public class LoginActivity_java extends AppCompatActivity implements View.OnClickListener {

    private TextView tvLast;
    private EditText etEmail;
    private EditText etPassword;
    private AppCompatButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_java);
        init();
    }

    private void init(){
        tvLast = findViewById(R.id.tv_last);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);

        String text = getString(R.string.tv_last);
        int purpleColor = ContextCompat.getColor(this, R.color.purple_500);

        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity_java.this, SignupActivity_java.class);
                startActivity(intent);
            }
        };

        int start = text.indexOf("sign up");
        int end = start + "sign up".length();
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(purpleColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvLast.setText(spannableString);
        tvLast.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private boolean validateEmailAndPassword(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please Enter a Valid Email");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password cannot be empty");
            return false;
        } else if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters long");
            return false;
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$")) {
            etPassword.setError("Password must contain at least one uppercase letter, one lowercase letter, and one digit");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_login){
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (validateEmailAndPassword(email, password)) {
                Toast.makeText(LoginActivity_java.this, "Login Successfully", Toast.LENGTH_LONG).show();
            }
        }
    }
}
