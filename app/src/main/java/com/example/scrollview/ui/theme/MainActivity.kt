package com.example.scrollview.ui.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scrollview.R
import android.os.Handler
import android.content.Intent

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


        Handler().postDelayed({
            // Check if user is already logged in
            val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

            if (isLoggedIn) {
                // Get saved user details
                val name = sharedPreferences.getString("name", "")
                val email = sharedPreferences.getString("email", "")
                val profileImageUrl = sharedPreferences.getString("profileImageUrl", "")

                // Go directly to HomeActivity
                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("email", email)
                    putExtra("profileImageUrl", profileImageUrl)
                }
                startActivity(intent)
            } else {
                // Go to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 1000)

    }
}
