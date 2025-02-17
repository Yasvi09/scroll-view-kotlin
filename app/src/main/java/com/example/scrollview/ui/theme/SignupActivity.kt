package com.example.scrollview.ui.theme

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.scrollview.R

class SignupActivity : AppCompatActivity() {
    lateinit var etname:EditText
    lateinit var etmobile:EditText
    lateinit var etpass:EditText
    lateinit var etconpass:EditText
    lateinit var btnSignup:AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        etname=findViewById(R.id.et_name)
        etmobile=findViewById(R.id.et_mobile)
        etpass=findViewById(R.id.sign_pass)
        etconpass=findViewById(R.id.sign_conpass)
        btnSignup=findViewById(R.id.btn_signup)

        btnSignup.setOnClickListener {
            val name=etname.text.toString().trim()
            val mobile=etmobile.text.toString().trim()
            val pass=etpass.text.toString().trim()
            val conpass=etconpass.text.toString().trim()
            if(validSignup(name,mobile,pass,conpass)){
                Toast.makeText(this@SignupActivity,"Sign up successfully !!",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun validSignup(name:String,mobile:String,pass:String,conpass:String):Boolean{
       if(name.isEmpty()){
            etname.error="Username cannot be empty"
            return false
       }
        else if(mobile.isEmpty()){
            etmobile.error="Mobile Number is required"
           return false
       }
        else if(!mobile.matches(Regex("^[0-9]{10}$"))){
            etmobile.error="Enter valid 10 digites mobile number"
            return false
       }
        else if(pass.isEmpty()){
            etpass.error="Password must not be empty"
           return false
       }
        else if(pass.length<6){
            etpass.error="Password must be atleast 6 digits"
            return false
       }
        else if(conpass.isEmpty()){
            etconpass.error="Confirm password is required"
            return false
       }
        else if(pass!=conpass){
            etconpass.error="Password not matched"
            return false
       }
        return true
    }
}