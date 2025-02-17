package com.example.scrollview.ui.theme;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.scrollview.R;

public class SignupActivity_java extends AppCompatActivity implements View.OnClickListener {

    private EditText etName;
    private EditText etMobile;
    private EditText etPass;
    private EditText etConPass;
    private AppCompatButton btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_java);

        init();
    }

    private void init(){
        etName = findViewById(R.id.et_name);
        etMobile = findViewById(R.id.et_mobile);
        etPass = findViewById(R.id.sign_pass);
        etConPass = findViewById(R.id.sign_conpass);
        btnSignup = findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(this);
    }

    private boolean validSignup(String name, String mobile, String pass, String conPass) {
        if (name.isEmpty()) {
            etName.setError("Username cannot be empty");
            return false;
        } else if (mobile.isEmpty()) {
            etMobile.setError("Mobile Number is required");
            return false;
        } else if (!mobile.matches("^[0-9]{10}$")) {
            etMobile.setError("Enter valid 10 digits mobile number");
            return false;
        } else if (pass.isEmpty()) {
            etPass.setError("Password must not be empty");
            return false;
        } else if (pass.length() < 6) {
            etPass.setError("Password must be at least 6 characters");
            return false;
        } else if (conPass.isEmpty()) {
            etConPass.setError("Confirm password is required");
            return false;
        } else if (!pass.equals(conPass)) {
            etConPass.setError("Password not matched");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
            if(v.getId()==R.id.btn_signup){
                String name = etName.getText().toString().trim();
                String mobile = etMobile.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String conPass = etConPass.getText().toString().trim();

                if (validSignup(name, mobile, pass, conPass)) {
                    Toast.makeText(SignupActivity_java.this, "Sign up successfully !!", Toast.LENGTH_SHORT).show();
                }
            }
    }
}
