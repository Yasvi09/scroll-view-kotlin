package com.example.scrollview.ui.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scrollview.R
import android.os.Handler
import android.content.Intent
import android.os.Looper
import android.util.Log

class   MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        Handler().postDelayed({
            val intent=Intent(this,BlankActivity::class.java)
            startActivity(intent)
            finish()
        },1000)*/


        Handler(Looper.getMainLooper()).postDelayed({
            println("Hello")
            // Check if user is already logged in
            // Check if user is already logged in
            val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

            println(isLoggedIn)

            if (isLoggedIn) {
                // If logged in, get saved user details and go to passcode verification
                val name = sharedPreferences.getString("name", "")
                val email = sharedPreferences.getString("email", "")
                val profileImageUrl = sharedPreferences.getString("profileImageUrl", "")

                // Go to PasscodeActivity for verification
                val intent = Intent(this, PasscodeActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("email", email)
                    putExtra("profileImageUrl", profileImageUrl)
                }
                startActivity(intent)
            } else {
                // If not logged in, go to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 1000)

    }
}
